package org.ds.health.dao;

import org.ds.health.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/12 17:16
 * @version: 1.0
 */
public interface OrderDao {
    List<Order> findByCondition(Order order);

    void add(Order order);

    Map<String, String> findById4Detail(int id);
}
