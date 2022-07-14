package com.lkd.vendingMachine;

import com.lkd.vo.UserVO;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@FeignClient("user-service")
public interface UserClient {
    /**
     * 设备属性莱查询所以有运营人员
     */
    @GetMapping("/user/operatorList/{innerCode}")
    ArrayList<UserVO> operatorList(@PathVariable String innerCode);

    /**
     * 设备属性莱查询所以有维修人员
     *
     * @param innerCode
     * @return
     */
    @GetMapping("/user/repairerList/{innerCode}")
    ArrayList<UserVO> getrepairerList(@PathVariable String innerCode);
}
