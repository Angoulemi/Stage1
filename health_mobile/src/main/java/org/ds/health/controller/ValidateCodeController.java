package org.ds.health.controller;

import com.aliyuncs.exceptions.ClientException;
import org.ds.health.constant.MessageConstant;
import org.ds.health.constant.RedisMessageConstant;
import org.ds.health.entity.Result;
import org.ds.health.utils.SMSUtils;
import org.ds.health.utils.ValidateCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/12 16:45
 * @version: 1.0
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    private static final Logger log = LoggerFactory.getLogger(ValidateCodeController.class);

    private final JedisPool jedisPool;

    public ValidateCodeController(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * @description: 发送手机验证码
     * @author: Deshan
     * @date: 2021/1/12 16:47
     * @param: [telephone]
     * @return: org.ds.health.entity.Result
     */
    @PostMapping("/send4Order")
    public Result send4Order(String telephone) {
        Jedis jedis = jedisPool.getResource();
        // 生成验证码检查是否发送过
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;

        if (jedis.exists(key)) {
            return new Result(false, "验证码已发送,请注意查收");
        }

        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        log.debug("========生成的验证码:{}", validateCode);

        try {
            SMSUtils.sendShortMessage(SMSUtils.TEMPLATE_CODE, telephone, validateCode + "");
        } catch (ClientException e) {
            log.error("发送验证码失败: {}:{}", telephone, validateCode);
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        jedis.setex(key, 10 * 60, validateCode + "");
        jedis.close();
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    @PostMapping("/send4Login")
    public Result send4Login(String telephone){
        Jedis jedis = jedisPool.getResource();

        String key = RedisMessageConstant.SENDTYPE_LOGIN + "_" + telephone;
        if (jedis.exists(key)) {

            return new Result(false, "验证码已经发送过了，请注意查收");
        }
        //- 不存在
        //  - 先生成验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        log.debug("========生成的验证码:{}",validateCode);
        //  - 调用SMSUtils发送验证码
//        try {
//            SMSUtils.sendShortMessage(SMSUtils.TEMPLATE_CODE,telephone,validateCode+"");
//        } catch (ClientException e) {
//            //e.printStackTrace();
//            log.error("发送验证码失败: {}:{}",telephone,validateCode);
//            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
//        }
        //  - 存入redis，设置有效期
        jedis.setex(key,10*60,validateCode+"");
        jedis.close();
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
