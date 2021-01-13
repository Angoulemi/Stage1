package org.ds.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ds.health.constant.MessageConstant;
import org.ds.health.entity.Result;
import org.ds.health.pojo.Setmeal;
import org.ds.health.service.SetmealService;
import org.ds.health.utils.COSUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/10 20:11
 * @version: 1.0
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {

    @Reference
    private SetmealService setmealService;

    /**
    * @description: 套餐列表
    * @author: Deshan
    * @date: 2021/1/10 20:16
    * @param: []
    * @return: org.ds.health.entity.Result
    */
    @GetMapping("/getSetmeal")
    public Result getSetmeal(){
        // 调用服务来查询所有的套餐
        List<Setmeal> list = setmealService.findAll();
        // 拼接图片的完整路径
        list.forEach(s->s.setImg(COSUtil.DOMAIN+s.getImg()));
        // 返回给页面
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,list);
    }

    /**
    * @description: 查询套餐详情
    * @author: Deshan
    * @date: 2021/1/10 20:16
    * @param: [id]
    * @return: org.ds.health.entity.Result
    */
    @GetMapping("/findDetailById")
    public Result findDetailById(int id){
        // 调用服务查询套餐详情
        Setmeal setmeal = setmealService.findDetailById(id);
        // 拼接图片的完整路径
        setmeal.setImg(COSUtil.DOMAIN+setmeal.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    }
}
