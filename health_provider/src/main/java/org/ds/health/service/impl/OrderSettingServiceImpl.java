package org.ds.health.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import org.ds.health.dao.OrderSettingDao;
import org.ds.health.exception.HealthException;
import org.ds.health.pojo.OrderSetting;
import org.ds.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/10 17:32
 * @version: 1.0
 */
@Service
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void addBatch(List<OrderSetting> orderSettingList) {
        //- 判断List<Ordersetting>不为空
        if(!CollectionUtils.isEmpty(orderSettingList)) {
            //- 遍历导入的预约设置信息List<Ordersetting>
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (OrderSetting os : orderSettingList) {
                //- 通过预约的日期来查询预约设置表，看这个日期的设置信息有没有
                editNumberByDate1(os, sdf);
            }
        }
    }

    @Override
    public List<Map<String, Integer>> getOrderSettingByMonth(String month) {
        month+="%";
        return orderSettingDao.getOrderSettingByMonth(month);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //- 通过预约的日期来查询预约设置表，看这个日期的设置信息有没有
        editNumberByDate1(orderSetting, sdf);
    }

    private void editNumberByDate1(OrderSetting orderSetting, SimpleDateFormat sdf) {
        OrderSetting osInDb = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
        //- 没有预约设置(表中没有这个日期的记录)
        if (null == osInDb) {
            //  - 调用dao插入数据
            orderSettingDao.add(orderSetting);
        } else {
            //- 有预约设置(表中有这个日期的记录)
            //  - 判断已预约人数是否大于要更新的最大预约数
            // 已预约人数
            int reservations = osInDb.getReservations();
            //要更新的最大预约数
            int number = orderSetting.getNumber();
            if (reservations > number) {
                //  - 大于则要报错，接口方法 异常声明
                throw new HealthException(sdf.format(orderSetting.getOrderDate()) + ": 最大预约数不能小于已预约人数");
            } else {
                //  - 小于，则可以更新最大预约数
                orderSettingDao.updateNumber(orderSetting);
            }
        }
    }
}
