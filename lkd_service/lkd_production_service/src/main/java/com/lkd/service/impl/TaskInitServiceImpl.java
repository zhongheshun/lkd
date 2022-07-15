package com.lkd.service.impl;

import com.alibaba.fastjson.JSON;
import com.lkd.common.VMSystem;
import com.lkd.config.TopicConfig;
import com.lkd.dtos.TaskSearchDTO;
import com.lkd.emq.Topic;
import com.lkd.feign.UserService;
import com.lkd.feign.VMService;
import com.lkd.service.TaskInitService;
import com.lkd.service.TaskService;
import com.lkd.vo.UserVO;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Set;

import static com.lkd.utils.UserRoleUtils.isOperator;
import static com.lkd.utils.UserRoleUtils.isRepair;

@Topic(TopicConfig.VMS_COMPLETED_TOPIC)
public class TaskInitServiceImpl implements MqttCallback, TaskInitService {

//    new Task

    @Autowired
    UserService userClient;

    @Autowired
    VMService vmService;

    @Autowired
    RedisTemplate redisTemplate;

    String WORKING_QUEUE = "TaskWorkingQueue";

    String WORKER_SALT = "WORKER_";

    //运营人员
    String DBO_WORKER = WORKER_SALT + "DBO_";

    //运维人员
    String OPS_WORKER = WORKER_SALT + "OPS_";

//    /**
//     * 更改工单状态
//     */
//    HttpStatus workStatusEditJobHandler(Integer taskId, Integer status) {
//
//        TaskEntity one = taskService.getById(taskId);
//
//        //设置状态
//        one.setTaskStatus(status);
//
//        //更新状态
//        taskService.updateById(one);
//
//        return HttpStatus.OK;
//    }

    /**
     * 初始化工单
     */
    @Override
    @XxlJob("workCountInitJobHandler")
    public HttpStatus workCountInitJobHandler(String param) {

        //删除hash表
        redisTemplate.opsForHash().delete(WORKING_QUEUE);

        //初始化
        List<UserVO> users = userClient.getUserList();
        users.stream().forEach(
                user -> {
                    //需要是启用状态
                    if (user.getStatus()) {

                        /**
                         * 拿到id和name，后续截取使用
                         */

                        //是否为运营人员
                        if (isOperator(user.getRoleId()))
                            redisTemplate.opsForHash().put(WORKING_QUEUE, DBO_WORKER + user.getRoleId() + "_" + user.getUserId() + "_" + user.getUserName(), 0);

                        //是否为运维人员
                        if (isRepair(user.getRoleId()))
                            redisTemplate.opsForHash().put(WORKING_QUEUE, DBO_WORKER + user.getRoleId() + "_" + OPS_WORKER + user.getUserId() + "_" + user.getUserName(), 0);

                    }
                }
        );

        return HttpStatus.OK;
    }

    @Autowired
    TaskService taskService;

    /**
     * 任务分配给最少任务的工人
     */
    @Override
    public void taskAllocation(TaskSearchDTO taskSearchDTO) {

        String minKey = null;
        Integer minValue = null;
        Integer count = 0;

        Set keys = redisTemplate.opsForHash().keys(WORKING_QUEUE);

        //工单类型：134是运维，2是运营
        String needWorkerType = taskSearchDTO.getProductTypeId() == VMSystem.TASK_TYPE_SUPPLY ? DBO_WORKER : OPS_WORKER;

        //比较出最少任务的工人  且人员类型需要符合
        for (Object key : keys) {

            //拿到机器ID查询所属点位  之后可以把点位作为条件匹配工人
            String containsKey = needWorkerType + vmService.getVMInfo(taskSearchDTO.getInnerCode());

            if (count == 0 && ((String) key).contains(containsKey)) {
                minKey = (String) key;
                minValue = (Integer) redisTemplate.opsForHash().get(WORKING_QUEUE, key);
            } else {
                Integer v = (Integer) redisTemplate.opsForHash().get(WORKING_QUEUE, key);
                if (v < minValue && ((String) key).contains(needWorkerType)) {
                    minKey = (String) key;
                    minValue = v;
                }
            }
        }

        //增加任务数
        redisTemplate.opsForHash().put(WORKING_QUEUE, minKey, ++minValue);

        String[] user_info = minKey.split("_");

        //设置执行人的id
        taskSearchDTO.setUserId(Integer.parseInt(user_info[user_info.length - 2]));
        //设置执行人的名字
        taskSearchDTO.setUserId(Integer.parseInt(user_info[user_info.length - 1]));

        //设置工单状态为创建  工人接单时就会为进行中 拒绝就是取消状态 完成自然是完成状态
        taskSearchDTO.setTaskStatus(VMSystem.TASK_STATUS_CREATE);

        //创建工单
        taskService.createTbTask(taskSearchDTO);
    }

    @Override
    public void connectionLost(Throwable throwable) {
        // 连接丢失后，一般在这里面进行重连
        System.out.println("连接断开，重连...");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // subscribe后得到的消息会执行到这里面
        System.out.println("接收消息主题:" + topic);
        System.out.println("接收消息Qos:" + message.getQos());

        String param = new String(message.getPayload());

        System.out.println("接收消息内容:" + param);

        //分配工单
        taskAllocation(JSON.parseObject(param, TaskSearchDTO.class));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
