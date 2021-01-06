package org.ds.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ds.health.constant.MessageConstant;
import org.ds.health.entity.PageResult;
import org.ds.health.entity.QueryPageBean;
import org.ds.health.entity.Result;

import org.ds.health.pojo.CheckItem;
import org.ds.health.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

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

    /**
    * @description: 查询所有选项
    * @author: Deshan
    * @date: 2021/1/6 20:28
    * @param: []
    * @return: org.ds.health.entity.Result
    */
    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckItem> list = checkItemService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }

    /**
    * @description: 添加检查项
    * @author: Deshan
    * @date: 2021/1/6 20:28
    * @param: [checkItem]
    * @return: org.ds.health.entity.Result
    */
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        checkItemService.add(checkItem);
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
    * @description: 分页
    * @author: Deshan
    * @date: 2021/1/6 20:29
    * @param: [queryPageBean]
    * @return: org.ds.health.entity.Result
    */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);

        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
    }

    /**
    * @description: 查询检查项
    * @author: Deshan
    * @date: 2021/1/6 20:29
    * @param: [id]
    * @return: org.ds.health.entity.Result
    */
    @GetMapping("/findById")
    public Result findById(int id){
        CheckItem checkItem = checkItemService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }

    /**
    * @description: 根据ID删除
    * @author: Deshan
    * @date: 2021/1/6 20:29
    * @param: [id]
    * @return: org.ds.health.entity.Result
    */
    @PostMapping("/deleteById")
    public Result deleteById(int id){
        checkItemService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    /**
    * @description: 更新选中项
    * @author: Deshan
    * @date: 2021/1/6 20:29
    * @param: [checkItem]
    * @return: org.ds.health.entity.Result
    */
    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem){
        checkItemService.update(checkItem);
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }
}
