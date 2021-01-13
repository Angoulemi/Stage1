package org.ds.health.service;

import org.ds.health.exception.HealthException;
import org.ds.health.pojo.Order;

import java.util.Map;

public interface OrderService {
    /**
     * 预约提交
     * @param orderInfo
     * @return
     */
    Order submitOrder(Map<String, String> orderInfo) throws HealthException;

    /**
     * 查询预约成功订单信息
     * @param id
     * @return
     */
    Map<String, String> findDetailById(int id);
}
