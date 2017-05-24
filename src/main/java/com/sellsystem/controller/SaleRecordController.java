package com.sellsystem.controller;

import com.sellsystem.entity.SaleRecord;
import com.sellsystem.service.SaleRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by zhangwei on 2017/5/23.
 */
@Controller
@RequestMapping("/saleRecord")
public class SaleRecordController {

    @Autowired
    private SaleRecordService saleRecordService;

    public List<SaleRecord> getList() {
        return saleRecordService.getList();
    }
}
