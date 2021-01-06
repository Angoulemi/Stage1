package org.ds.health.service;

import org.ds.health.entity.PageResult;
import org.ds.health.entity.QueryPageBean;
import org.ds.health.pojo.CheckGroup;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/6 20:00
 * @version: 1.0
 */
public interface CheckGroupService {
    /**
     * @description: 添加选择组
     * @author: Deshan
     * @date: 2021/1/6 20:30
     * @param: [checkgroup，checkitemIds]
     * @return: void
     */
    void add(CheckGroup checkgroup, Integer[] checkitemIds);

    /**
    * @description: 分页查询
    * @author: Deshan
    * @date: 2021/1/6 20:41
    * @param: [queryPageBean]
    * @return: org.ds.health.entity.PageResult<org.ds.health.pojo.CheckGroup>
    */
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    /**
    * @description: 查询
    * @author: Deshan
    * @date: 2021/1/6 20:41
    * @param: [id]
    * @return: org.ds.health.pojo.CheckGroup
    */
    CheckGroup findById(int id);

    /**
    * @description: 关联的检查项ID
    * @author: Deshan
    * @date: 2021/1/6 20:42
    * @param: [id]
    * @return: java.util.List<java.lang.Integer>
    */
    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    /**
    * @description: 更新
    * @author: Deshan
    * @date: 2021/1/6 20:42
    * @param: [checkgroup, checkitemIds]
    * @return: void
    */
    void update(CheckGroup checkgroup, Integer[] checkitemIds);
}
