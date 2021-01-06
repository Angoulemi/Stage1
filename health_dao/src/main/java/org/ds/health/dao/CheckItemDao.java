package org.ds.health.dao;

import com.github.pagehelper.Page;
import org.ds.health.pojo.CheckItem;

import java.util.List;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/5 17:52
 * @version: 1.0
 */
public interface CheckItemDao {

    /**
    * @description: 查询全部
    * @author: Deshan
    * @date: 2021/1/6 20:37
    * @param: []
    * @return: java.util.List<org.ds.health.pojo.CheckItem>
    */
    List<CheckItem> findAll();

    /**
    * @description: 模糊查询
    * @author: Deshan
    * @date: 2021/1/6 20:37
    * @param: [queryString]
    * @return: com.github.pagehelper.Page<org.ds.health.pojo.CheckItem>
    */
    Page<CheckItem> findByCondition(String queryString);

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
    * @description: 查询选择项是否有关联
    * @author: Deshan
    * @date: 2021/1/6 20:38
    * @param: [id]
    * @return: int
    */
    int findCountByCheckItemId(int id);

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
