package org.ds.health.service;

import org.ds.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/10 17:34
 * @version: 1.0
 */
public interface OrderSettingService {
    void addBatch(List<OrderSetting> orderSettingList);

    List<Map<String, Integer>> getOrderSettingByMonth(String month);

    void editNumberByDate(OrderSetting orderSetting);
}
