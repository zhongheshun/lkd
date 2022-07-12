package com.lkd.http.controller;
import com.lkd.entity.OrderCollectEntity;
import com.lkd.service.ReportService;
import com.lkd.vo.BarCharVO;
import com.lkd.vo.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 报表controller
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;


}
