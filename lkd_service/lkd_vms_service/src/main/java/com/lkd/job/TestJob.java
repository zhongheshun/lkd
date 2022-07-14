package com.lkd.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

@Component
public class TestJob {
    @XxlJob("hahajobhander")
    public void kk(){
        for (int i = 0; i < 100; i++) {
            System.out.println("我是XXLJOB！hAha!你中计了！");
        }
    }
}
