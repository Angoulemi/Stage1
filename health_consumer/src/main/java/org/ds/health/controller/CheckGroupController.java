package org.ds.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ds.health.constant.MessageConstant;
import org.ds.health.entity.PageResult;
import org.ds.health.entity.QueryPageBean;
import org.ds.health.entity.Result;
import org.ds.health.pojo.CheckGroup;
import org.ds.health.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/6 20:00
 * @version: 1.0
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    /**
    * @description: 添加选择组
    * @author: Deshan
    * @date: 2021/1/6 20:28
    * @param: [checkgroup, checkitemIds]
    * @return: org.ds.health.entity.Result
    */
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkgroup, Integer[] checkitemIds){
        // 调用服务 添加检查组
        checkGroupService.add(checkgroup, checkitemIds);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
    * @description: 分页
    * @author: Deshan
    * @date: 2021/1/6 20:28
    * @param: [queryPageBean]
    * @return: org.ds.health.entity.Result
    */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        // 调用服务 分页查询
        PageResult<CheckGroup> pageResult = checkGroupService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }

    /**
    * @description: 查询选择组
    * @author: Deshan
    * @date: 2021/1/6 20:28
    * @param: [id]
    * @return: org.ds.health.entity.Result
    */
    @GetMapping("/findById")
    public Result findById(int id){
        CheckGroup checkGroup = checkGroupService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }

    /**
    * @description: 根据选中的Id查询选择组
    * @author: Deshan
    * @date: 2021/1/6 20:27
    * @param: [id]
    * @return: org.ds.health.entity.Result
    */
    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(int id){
        // 通过检查组id查询选中的检查项id集合
        List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemIds);
    }

    /**
    * @description: 修改检查组
    * @author: Deshan
    * @date: 2021/1/6 20:26
    * @param: [checkgroup, checkitemIds]
    * @return: org.ds.health.entity.Result
    */
    @PostMapping("/update")
    public Result update(@RequestBody CheckGroup checkgroup, Integer[] checkitemIds){
        checkGroupService.update(checkgroup, checkitemIds);
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    /**
    * @description: 查所有检查组
    * @author: Deshan
    * @date: 2021/1/8 19:59
    * @param: []
    * @return: org.ds.health.entity.Result
    */
    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckGroup> list = checkGroupService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
    }

}
