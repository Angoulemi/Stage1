package org.ds.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ds.health.constant.MessageConstant;
import org.ds.health.entity.Result;
import org.ds.health.pojo.OrderSetting;
import org.ds.health.service.OrderSettingService;
import org.ds.health.utils.POIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/10 17:31
 * @version: 1.0
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    private static final Logger log = LoggerFactory.getLogger(OrderSettingController.class);

    @Reference
    private OrderSettingService orderSettingService;

    /**
    * @description: 批量导入预约设置
    * @author: Deshan
    * @date: 2021/1/13 19:53
    * @param: [excelFile]
    * @return: org.ds.health.entity.Result
    */
    @PostMapping("/upload")
    public Result upload(MultipartFile excelFile){
        try {
            List<String[]> excelData = POIUtils.readExcel(excelFile);
            log.debug("导入预约设置读取到了{}条记录",excelData.size());
            //转成List<Ordersetting>，再调用service 方法做导入，返回给页面
            // String[] 长度为2, 0:日期，1：数量
            final SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            List<OrderSetting> orderSettingList = excelData.stream().map(arr -> {
                OrderSetting os = new OrderSetting();
                try {
                    os.setOrderDate(sdf.parse(arr[0]));
                    os.setNumber(Integer.parseInt(arr[1]));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return os;
            }).collect(Collectors.toList());
            // 调用服务导入
            orderSettingService.addBatch(orderSettingList);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            log.error("导入预约设置失败",e);
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }


    /**
    * @description: 通过月份查询预约设置信息
    * @author: Deshan
    * @date: 2021/1/13 19:53
    * @param: [month]
    * @return: org.ds.health.entity.Result
    */
    @GetMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month){
        // 按月查询预约设置信息
        List<Map<String,Integer>> data = orderSettingService.getOrderSettingByMonth(month);
        return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS,data);
    }


    /**
    * @description: 通过日期设置可预约的最大数
    * @author: Deshan
    * @date: 2021/1/13 19:53
    * @param: [orderSetting]
    * @return: org.ds.health.entity.Result
    */
    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        // 调用服务更新
        orderSettingService.editNumberByDate(orderSetting);
        return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
    }
}
