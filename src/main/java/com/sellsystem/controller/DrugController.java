package com.sellsystem.controller;

import com.sellsystem.entity.Drug;
import com.sellsystem.entity.DrugRecord;
import com.sellsystem.entity.Warehouse;
import com.sellsystem.entity.searchmodel.extend.DrugSearchModel;
import com.sellsystem.entity.searchmodel.extend.SaleProfitSearchModel;
import com.sellsystem.entity.searchmodel.extend.WarehouseSearchModel;
import com.sellsystem.service.DrugService;
import com.sellsystem.service.SaleProfitService;
import com.sellsystem.service.SaleRecordService;
import com.sellsystem.service.WarehouseService;
import com.sellsystem.util.DateUtil;
import com.sellsystem.util.ExportUtil;
import com.sellsystem.util.MsgModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 药品
 * Created by Administrator on 2017/3/4/004.
 */
@Controller
@RequestMapping(value = "/drug")
public class DrugController {

    @Autowired
    private DrugService drugService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private SaleRecordService saleRecordService;
    @Autowired
    private SaleProfitService saleProfitService;

    /**
     * 列表
     *
     * @param model
     * @param drugSearchModel
     * @return
     */
    @GetMapping("")
    public String list(Model model, DrugSearchModel drugSearchModel) {
        if (StringUtils.isEmpty(drugSearchModel.getWarehouseId())) {
            drugSearchModel.setWarehouseId(null);
        }
        model.addAttribute("drugList", drugService.getList(drugSearchModel).getData().getList());

        //搜索条件
        model.addAttribute("drugSearchModel", drugSearchModel);

        //仓库列表
        WarehouseSearchModel warehouseSearchModel = new WarehouseSearchModel();
        warehouseSearchModel.setPageSize(0);
        List<Map<?, ?>> warehouseList = warehouseService.getList(warehouseSearchModel).getData().getList();
        model.addAttribute("warehouseList", warehouseList);

        //仓库中药品总数量
        int drugAllNum = 0;
        for (Map<?, ?> warehouseMap : warehouseList) {
            drugAllNum += Integer.parseInt(warehouseMap.get("drugNum").toString());
        }
        model.addAttribute("drugAllNum", drugAllNum);
        return "drug/list";
    }

    /**
     * 详情
     *
     * @param model
     * @param drugId
     * @return
     */
    @GetMapping("/view")
    public String view(Model model, String drugId) {
        Drug drug = drugService.getDrug(drugId).getData();
        model.addAttribute("drug", drugService.getDrug(drugId).getData());
        //有效期和现在的差值，用来提示是否过期
        long day = (drug.getPeriod().getTime() -  DateUtil.getCurrentDayDate().getTime())/(1000*60*60*24);
        if (day < 0) {
            model.addAttribute("day", "该药品已过期，请尽快处理！");
        } else if (day <= 30) {
            model.addAttribute("day", "该药品即将过期，请尽快处理！");
        }
        return "drug/view";
    }

    /**
     * 新增
     *
     * @param drug
     * @return
     */
    @PostMapping("/create")
    public String create(Drug drug) {
        drugService.create(drug);
        return "/drug/edit";
    }

    @PutMapping("/update")
    public String three(Drug drug) {
        drugService.update(drug);
        return "/drug/edit";
    }

    @GetMapping("/analyse")
    public String analyse(Model model) {
        model.addAttribute("drugList", drugService.getDrugLessThan30());
        model.addAttribute("sortSumList", drugService.getSumBySort());
        return "/drug/analyse";
    }

    @GetMapping("/profit")
    public String profit(Model model, SaleProfitSearchModel saleProfitSearchModel) {
        model.addAttribute("saleProfitSearchModel", saleProfitSearchModel);
        model.addAttribute("saleProfitList", saleProfitService.getList(saleProfitSearchModel));
        return "/drug/profit";
    }

    /**
     * 导出
     * @param ids
     * @param response
     */
    @GetMapping("/export")
    public void export(String ids, HttpServletResponse response) {
        List<Drug> drugList = new ArrayList<>();
        if (!StringUtils.isEmpty(ids)) {
            String[] idArr = ids.split(",");
            for (String id : idArr) {
                Drug drug = drugService.getDrug(id).getData();
                drugList.add(drug);
            }
        } else {
            drugList.addAll(drugService.getList(new DrugSearchModel().init()).getData().getList());
        }

        response.setContentType("octets/stream");
        response.addHeader("Content-Disposition", "attachment;filename=药品列表.xls");
        ExportUtil<Drug> ex = new ExportUtil<>();
        String[] headers = {"药品名称", "药品单位", "药品数量", "进价（元）", "零售价（元）", "药品类别", "所属仓库", "采购者", "供药商", "有效期", "药品产地"};

        //这里把数据重新敷了一下值
        //List<Drug> amList = drugService.exportExcel(dataset);
        try {
            OutputStream out = response.getOutputStream();
            ex.exportExcel(headers, drugList, out);
            out.close();
            System.out.println("excel导出成功！");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 退药
     * @param drugId
     * @param type
     * @param drugNum
     * @param remark
     * @return
     */
    @ResponseBody
    @PostMapping("/out")
    public MsgModel out(String drugId, String type, int drugNum, String remark) {
        return drugService.outDrug(drugId, type, drugNum, remark);
    }

    @ResponseBody
    @GetMapping("/record/list")
    public List<DrugRecord> drugRecords(String drugId, int pageNumber, int pageSize) {
        List<DrugRecord> recordList = drugService.getDrugRecord(drugId, pageNumber, pageSize);
        return drugService.getDrugRecord(drugId, pageNumber, pageSize);
    }

    @GetMapping("/delete")
    public String delete(String drugId) {
        drugService.delete(drugId);
        return "redirect:/drug";
    }

}
