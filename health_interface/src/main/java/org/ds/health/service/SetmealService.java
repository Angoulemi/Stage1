package org.ds.health.service;

import org.ds.health.entity.PageResult;
import org.ds.health.entity.QueryPageBean;
import org.ds.health.exception.HealthException;
import org.ds.health.pojo.Setmeal;

import java.util.List;

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
}
