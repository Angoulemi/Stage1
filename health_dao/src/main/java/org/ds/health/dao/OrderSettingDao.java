package org.ds.health.dao;

import org.ds.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/10 17:59
 * @version: 1.0
 */
public interface OrderSettingDao {
    OrderSetting findByOrderDate(Date orderDate);

    void add(OrderSetting os);

    void updateNumber(OrderSetting os);

    List<Map<String, Integer>> getOrderSettingByMonth(String month);

    int editReservationsByOrderDate(OrderSetting osInDb);
}
