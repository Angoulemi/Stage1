package org.ds.health.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
* @description: 短信发送工具类
* @author: Deshan
* @date: 2021/1/12 16:40
*/
public class SMSUtils {
    //发送短信验证码模板编码  模版CODE
    public static final String TEMPLATE_CODE = "SMS_200183554";
    // 短信的签名
    private static final String SIGN_NAEM = "CHOPE";

    private static final String PARAMETER_NAME = "code";
    //你的AccessKey ID
    private static final String ACCESS_KEY = "LTAI4G88uqWRjqK26ZLh1dDu";
    //你的AccessKey Secret
    private static final String SECRET_KEY = "tMCFnbGggBkQDMUYYQ4KLfMRdOidwh";

    public static void main(String[] args) throws ClientException {
        SMSUtils.sendShortMessage(TEMPLATE_CODE,"16607770549","666666");
    }

    public static void sendShortMessage(String templateCode,String phoneNumbers, String param) throws ClientException {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY, SECRET_KEY);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("SignName", SIGN_NAEM);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + param + "\"}");
        CommonResponse response = client.getCommonResponse(request);
    }
}
