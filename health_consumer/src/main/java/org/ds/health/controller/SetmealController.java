package org.ds.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ds.health.constant.MessageConstant;
import org.ds.health.entity.PageResult;
import org.ds.health.entity.QueryPageBean;
import org.ds.health.entity.Result;
import org.ds.health.pojo.Setmeal;
import org.ds.health.service.SetmealService;
import org.ds.health.utils.COSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/8 17:12
 * @version: 1.0
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    private static Logger log = LoggerFactory.getLogger(SetmealController.class);

    @Reference
    private SetmealService setmealService;

    /**
    * @description: 上传文件(以及回显)
    * @author: Deshan
    * @date: 2021/1/8 19:52
    * @param: [imgFile]
    * @return: org.ds.health.entity.Result
    */
    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        //1. 获取源文件名
        String originalFilename = imgFile.getOriginalFilename();
        //2. 截取后缀名
        String suffix = Objects.requireNonNull(originalFilename).substring(originalFilename.lastIndexOf("."));
        //3. 生成唯一id
        String uniqueId = UUID.randomUUID().toString();
        //4. 拼接唯一文件名
        String filename = uniqueId + suffix;


        try {
            COSUtil.simpleUpload(imgFile.getInputStream(), filename, imgFile.getSize());
            Map<String, String> map = new HashMap<>(4);
            map.put("imgName", filename);
            map.put("domain", COSUtil.DOMAIN);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, map);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }


    /**
    * @description: 添加套餐
    * @author: Deshan
    * @date: 2021/1/8 19:42
    * @param: [setmeal, checkgroupIds]
    * @return: org.ds.health.entity.Result
    */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        // 调用服务添加套餐
        setmealService.add(setmeal, checkgroupIds);
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
    * @description: 分页条件
    * @author: Deshan
    * @date: 2021/1/8 19:41
    * @param: [queryPageBean]
    * @return: org.ds.health.entity.Result
    */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<Setmeal> setmealPageResult = setmealService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmealPageResult);
    }

    /**
    * @description: 查
    * @author: Deshan
    * @date: 2021/1/8 19:51
    * @param: [id]
    * @return: org.ds.health.entity.Result
    */
    @GetMapping("/findById")
    public Result findById(int id) {
        // 套餐信息
        Setmeal setmeal = setmealService.findById(id);
        // 构建前端需要的数据, 还要有域名
        Map<String, Object> map = new HashMap<>(4);
        map.put("setmeal", setmeal);
        map.put("domain", COSUtil.DOMAIN);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, map);
    }

    /**
    * @description: 查询关联项
    * @author: Deshan
    * @date: 2021/1/8 19:51
    * @param: [id]
    * @return: org.ds.health.entity.Result
    */
    @GetMapping("/findCheckGroupIdsBySetmealId")
    public Result findCheckGroupIdsBySetmealId(int id) {
        List<Integer> checkgroupIds = setmealService.findCheckGroupIdsBySetmealId(id);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, checkgroupIds);
    }

    /**
    * @description: 改
    * @author: Deshan
    * @date: 2021/1/8 19:51
    * @param: [setmeal, checkgroupIds]
    * @return: org.ds.health.entity.Result
    */
    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        // 调用服务修改套餐
        setmealService.update(setmeal, checkgroupIds);
        return new Result(true, "编辑套餐成功");
    }

    /**
    * @description: 删
    * @author: Deshan
    * @date: 2021/1/8 19:51
    * @param: [id]
    * @return: org.ds.health.entity.Result
    */
    @PostMapping("/deleteById")
    public Result deleteById(int id) {
        setmealService.deleteById(id);
        return new Result(true, "删除套餐成功");
    }

}
