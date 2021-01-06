package org.ds.health.service;

import org.ds.health.pojo.CheckItem;

import java.util.List;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/5 18:07
 * @version: 1.0
 */
public interface CheckItemService {

    List<CheckItem> findAll();
}
