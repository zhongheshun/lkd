package com.lkd.http.controller;
import com.lkd.config.WXConfig;
import com.lkd.utils.OpenIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {


    @Autowired
    private WXConfig wxConfig;

    /**
     * 获取openId
     * @param jsCode
     * @return
     */
    @GetMapping("/openid/{jsCode}")
    public String getOpenid(@PathVariable("jsCode")  String jsCode){
        return OpenIDUtil.getOpenId( wxConfig.getAppId(),wxConfig.getAppSecret(),jsCode );
    }

}
