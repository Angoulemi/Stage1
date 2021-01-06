package org.ds.health.service;

import org.ds.health.entity.PageResult;
import org.ds.health.entity.QueryPageBean;
import org.ds.health.pojo.CheckItem;

import java.util.List;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/5 18:07
 * @version: 1.0
 */
public interface CheckItemService {

    /**
     * @description: 查询所有项
     * @author: Deshan
     * @date: 2021/1/6 17:04
     * @param: []
     * @return: java.util.List<org.ds.health.pojo.CheckItem>
     */
    List<CheckItem> findAll();

    /**
     * @description: 分页查询
     * @author: Deshan
     * @date: 2021/1/6 17:05
     * @param: [queryPageBean]
     * @return: org.ds.health.entity.PageResult<org.ds.health.pojo.CheckItem>
     */
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    /**
     * @description: 添加检查项
     * @author: Deshan
     * @date: 2021/1/6 20:37
     * @param: [checkItem]
     * @return: void
     */
    void add(CheckItem checkItem);

    /**
     * @description: 删除检查项
     * @author: Deshan
     * @date: 2021/1/6 20:37
     * @param: [id]
     * @return: void
     */
    void deleteById(int id);

    /**
     * @description: 更新检查项
     * @author: Deshan
     * @date: 2021/1/6 20:38
     * @param: [checkItem]
     * @return: void
     */
    void update(CheckItem checkItem);

    /**
     * @description: 查询检查项
     * @author: Deshan
     * @date: 2021/1/6 20:38
     * @param: [id]
     * @return: org.ds.health.pojo.CheckItem
     */
    CheckItem findById(int id);
}
