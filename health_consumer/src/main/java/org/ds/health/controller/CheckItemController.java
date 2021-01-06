package org.ds.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ds.health.constant.MessageConstant;
import org.ds.health.entity.Result;

import org.ds.health.pojo.CheckItem;
import org.ds.health.service.CheckItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/5 18:12
 * @version: 1.0
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    @GetMapping("/findALL")
    public Result findAll(){
        List<CheckItem> list = checkItemService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }
}
