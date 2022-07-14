package com.lkd.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lkd.dao.TaskDao;
import com.lkd.dtos.TaskSearchDTO;
import com.lkd.entity.TaskDetailsEntity;
import com.lkd.entity.TaskEntity;
import com.lkd.service.TaskDetailsService;
import com.lkd.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
@Slf4j
public class TaskServiceImpl extends ServiceImpl<TaskDao, TaskEntity> implements TaskService {


    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    TaskDetailsService taskDetailsService;

    /**
     * 创建工单
     */
    @Override
    public void createTbTask(TaskSearchDTO tbTaskDto) {
        TaskEntity tbTask = new TaskEntity();

        String orderNumber = getOrderNumber();
        tbTask.setTaskCode(orderNumber);

        BeanUtils.copyProperties(tbTaskDto,tbTask);
        this.save(tbTask);

        /**
         * 前端补货信息传的是object[]所以通过索引对应的索引进行赋值添加
         */
        if (tbTaskDto.getDetails() != null && tbTaskDto.getDetails().length == 5){
            /**
             * 之后存入补货信息需要与刚刚存入的工单id产生关联
             */
            TaskEntity task = this.getOne(Wrappers.<TaskEntity>lambdaQuery().eq(TaskEntity::getTaskCode, orderNumber));

            Object[] details = tbTaskDto.getDetails();
            TaskDetailsEntity tde = new TaskDetailsEntity();

            tde.setChannelCode((String)details[0]);
            tde.setExpectCapacity((Integer) details[1]);
            tde.setSkuId(Long.parseLong((String)details[2]));
            tde.setSkuName((String)details[3]);
            tde.setSkuImage((String)details[4]);
            tde.setTaskId(task.getTaskId());

            taskDetailsService.save(tde);
        }
    }

    /**
     *
     */

    /**
     * [生成唯一的编号]
     * 编号 = 当前的日期 + 递增的一个原子性的long值
     * 每天都会从0开始原子性的递增Redis中的这个long值，(例如：2020071200000001)
     * 设定每天的23:59:59 Redis中的这个long值 都会过期
     * 因此保证了每天的long值原子性的从0开始递增且不重复，然后拼接上每天都会改变且不重复的日期，保证了每个编号的唯一性
     *
     */
    public String getOrderNumber() {

        String key = "TaskNumber";

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        //设置过期时间，这里设置为当天的23:59:59
        Date expireDate = calendar.getTime();

        //返回当前redis中的key的最大值
        Long seq = generate(key, expireDate);
        //获取当天的日期，格式为yyyyMMdd
        String date = new SimpleDateFormat("yyyyMMdd").format(expireDate);
        //生成八为的序列号，如果seq不够八位，seq前面补0，
        //如果seq位数超过了八位，那么无需补0直接返回当前的seq
        String sequence = StringUtils.leftPad(seq.toString(), 8, "0");
        //拼接业务编号
        String seqNo = date + sequence;
        log.error("KEY:{}, 序列号生成:{}, 过期时间:{}", key ,seqNo, String.format("%tF %tT ", expireDate, expireDate));
        return seqNo;
    }

    /**
     * @param key
     * @param expireTime <i>过期时间</i>
     * @return
     */
    public long generate(String key, Date expireTime) {
        //RedisAtomicLong为原子类，根据传入的key和redis链接工厂创建原子类
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        //设置过期时间
        counter.expireAt(expireTime);
        //返回redis中key的值，内部实现下面详细说明
        return counter.incrementAndGet();
    }

}
