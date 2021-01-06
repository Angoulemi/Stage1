package org.ds.health.dao;

import org.ds.health.pojo.CheckItem;

import java.util.List;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/5 17:52
 * @version: 1.0
 */
public interface CheckItemDao {

    List<CheckItem> findAll();
}
