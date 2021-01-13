package org.ds.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ds.health.constant.MessageConstant;
import org.ds.health.constant.RedisMessageConstant;
import org.ds.health.entity.Result;
import org.ds.health.pojo.Order;
import org.ds.health.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/12 17:02
 * @version: 1.0
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);


    private final JedisPool jedisPool;

    public OrderController(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Reference
    private OrderService orderService;

    /**
     * 提交预约
     * @param orderInfo
     * @return
     */
    @PostMapping("/submit")
    public Result submit(@RequestBody Map<String,String> orderInfo){
        String telephone = orderInfo.get("telephone");
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;
        Jedis jedis = jedisPool.getResource();
        String codeInRedis = jedis.get(key);
        log.info("codeInRedis:{}",codeInRedis);

        if(StringUtils.isEmpty(codeInRedis)) {

            return new Result(false, "请重新获取验证码!");
        }
        log.info("codeFromUI:{}",orderInfo.get("validateCode"));

        if(!codeInRedis.equals(orderInfo.get("validateCode"))) {
            return new Result(false, "验证码不正确!");
        }
        //删除key，防止重复提交
        jedis.del(key);
        // 设置预约的类型，health_mobile, 微信预约
        orderInfo.put("orderType", Order.ORDERTYPE_WEIXIN);
        // 调用预约服务
        Order order = orderService.submitOrder(orderInfo);
        // 返回结果给页面，返回订单信息
        return new Result(true, MessageConstant.ORDER_SUCCESS,order);
    }

    /**
    * @description: 查询预约成功订单信息
    * @author: Deshan
    * @date: 2021/1/12 20:08
    * @param: [id]
    * @return: org.ds.health.entity.Result
    */
    @GetMapping("/findById")
    public Result findById(int id){
        Map<String,String> orderInfo = orderService.findDetailById(id);
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,orderInfo);
    }
}
