package org.ds.health.dao;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.ds.health.pojo.CheckGroup;

import java.util.List;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/6 20:03
 * @version: 1.0
 */
public interface CheckGroupDao {
    /**
    * @description: 添加选择组
    * @author: Deshan
    * @date: 2021/1/6 20:30
    * @param: [checkgroup]
    * @return: void
    */
    void add(CheckGroup checkgroup);

    /**
    * @description: 选择组与选择项的关系
    * @author: Deshan
    * @date: 2021/1/6 20:30
    * @param: [checkgroupId, checkitemId]
    * @return: void
    */
    void addCheckGroupCheckItem(@Param("checkgroupId") Integer checkgroupId, @Param("checkitemId") Integer checkitemId);

    /**
    * @description: 模糊查询
    * @author: Deshan
    * @date: 2021/1/6 20:30
    * @param: [queryString]
    * @return: com.github.pagehelper.Page<org.ds.health.pojo.CheckGroup>
    */
    Page<CheckGroup> findByCondition(String queryString);

    /**
    * @description: 查询检查项
    * @author: Deshan
    * @date: 2021/1/6 20:31
    * @param: [id]
    * @return: org.ds.health.pojo.CheckGroup
    */
    CheckGroup findById(int id);

    /**
    * @description: 通过检查组id查询选中的检查项id
    * @author: Deshan
    * @date: 2021/1/6 20:31
    * @param: [id]
    * @return: java.util.List<java.lang.Integer>
    */
    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    /**
    * @description: 更新检查组
    * @author: Deshan
    * @date: 2021/1/6 20:31
    * @param: [checkgroup]
    * @return: void
    */
    void update(CheckGroup checkgroup);

    /**
    * @description: 删除修改时的关系依赖
    * @author: Deshan
    * @date: 2021/1/6 20:33
    * @param: [id]
    * @return: void
    */
    void deleteCheckGroupCheckItem(Integer id);

    /**
    * @description: 查所有检查组
    * @author: Deshan
    * @date: 2021/1/8 20:03
    * @param: []
    * @return: java.util.List<org.ds.health.pojo.CheckGroup>
    */
    List<CheckGroup> findAll();

    /**
    * @description: 删
    * @author: Deshan
    * @date: 2021/1/8 21:07
    * @param: [id]
    * @return: void
    */
    void deleteById(int id);

    /**
    * @description: 查找关联项
    * @author: Deshan
    * @date: 2021/1/8 21:08
    * @param: [id]
    * @return: int
    */
    int findCountByCheckGroupId(int id);
}
