package com.lkd.vendingMachine;

import com.lkd.vo.SkuVO;
import com.lkd.vo.VmVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("vm-service")
public interface VmClient {
    @GetMapping("/vm/info/{innerCode}")
    VmVO getByInnerCode(@PathVariable("innerCode") String innerCode);
}
