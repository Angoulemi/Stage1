package org.ds.health.dao;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
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
public interface SetmealDao {
    /**
    * @description: 增
    * @author: Deshan
    * @date: 2021/1/8 19:50
    * @param: [setmeal]
    * @return: void
    */
    void add(Setmeal setmeal);

    /**
    * @description: 添加关联项信息
    * @author: Deshan
    * @date: 2021/1/8 19:50
    * @param: [setmealId, checkgroupId]
    * @return: void
    */
    void addSetmealCheckGroup(@Param("setmealId") Integer setmealId, @Param("checkgroupId") Integer checkgroupId);

    /**
    * @description: 条件查
    * @author: Deshan
    * @date: 2021/1/8 19:50
    * @param: [queryString]
    * @return: com.github.pagehelper.Page<org.ds.health.pojo.Setmeal>
    */
    Page<Setmeal> findByCondition(String queryString);

    /**
    * @description: 查
    * @author: Deshan
    * @date: 2021/1/8 19:50
    * @param: [id]
    * @return: org.ds.health.pojo.Setmeal
    */
    Setmeal findById(int id);

    /**
    * @description: 查询关联项ID
    * @author: Deshan
    * @date: 2021/1/8 19:50
    * @param: [id]
    * @return: java.util.List<java.lang.Integer>
    */
    List<Integer> findCheckGroupIdsBySetmealId(int id);

    /**
    * @description: 改
    * @author: Deshan
    * @date: 2021/1/8 19:49
    * @param: [setmeal]
    * @return: void
    */
    void update(Setmeal setmeal);

    /**
    * @description: 删
    * @author: Deshan
    * @date: 2021/1/8 19:49
    * @param: [id]
    * @return: void
    */
    void deleteSetmealCheckGroup(Integer id);

    /**
    * @description: 查询是否有关联项
    * @author: Deshan
    * @date: 2021/1/8 19:49
    * @param: [id]
    * @return: int
    */
    int findCountBySetmealId(int id);

    /**
    * @description: 删
    * @author: Deshan
    * @date: 2021/1/8 19:49
    * @param: [id]
    * @return: void
    */
    void deleteById(int id);

}
