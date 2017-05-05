package com.sellsystem.controller;

import com.sellsystem.entity.Record;
import com.sellsystem.entity.searchmodel.extend.RecordSearchModel;
import com.sellsystem.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zhangwei on 2017/5/2.
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @ResponseBody
    @GetMapping("/list")
    public List<Record> recordList(RecordSearchModel recordSearchModel) {
        return recordService.getList(recordSearchModel);
    }
}
