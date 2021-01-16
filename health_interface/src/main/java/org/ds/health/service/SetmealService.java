package org.ds.health.service;

import org.ds.health.entity.PageResult;
import org.ds.health.entity.QueryPageBean;
import org.ds.health.exception.HealthException;
import org.ds.health.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author: Eric
 * @since: 2021/1/8
 */
public interface SetmealService {
    /**
    * @description: 增
    * @author: Deshan
    * @date: 2021/1/8 19:48
    * @param: [setmeal, checkgroupIds]
    * @return: void
    */
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    /**
    * @description: 分页
    * @author: Deshan
    * @date: 2021/1/8 19:48
    * @param: [queryPageBean]
    * @return: org.ds.health.entity.PageResult<org.ds.health.pojo.Setmeal>
    */
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    /**
    * @description: 查
    * @author: Deshan
    * @date: 2021/1/8 19:48
    * @param: [id]
    * @return: org.ds.health.pojo.Setmeal
    */
    Setmeal findById(int id);

    /**
    * @description: 查询关联项
    * @author: Deshan
    * @date: 2021/1/8 19:48
    * @param: [id]
    * @return: java.util.List<java.lang.Integer>
    */
    List<Integer> findCheckGroupIdsBySetmealId(int id);

    /**
    * @description: 改
    * @author: Deshan
    * @date: 2021/1/8 19:48
    * @param: [setmeal, checkgroupIds]
    * @return: void
    */
    void update(Setmeal setmeal, Integer[] checkgroupIds);

    /**
    * @description: 删
    * @author: Deshan
    * @date: 2021/1/8 19:48
    * @param: [id]
    * @return: void
    */
    void deleteById(int id) throws HealthException;

    /**
    * @description: 查询套餐的所有图片
    * @author: Deshan
    * @date: 2021/1/9 20:16
    * @param: []
    * @return: java.util.List<java.lang.String>
    */
    List<String> findImgs();

    List<Setmeal> findAll();

    Setmeal findDetailById(int id);

    List<Map<String, Object>> getSetmealReport();
}
