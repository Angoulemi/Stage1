package org.ds.health.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.ds.health.constant.MessageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/8 17:29
 * @version: 1.0
 */
public class COSUtil {


    public static final String DOMAIN = "https://sunstrider-1302699626.cos.ap-nanjing.myqcloud.com/";

    public static final String BUCKET_NAME = "sunstrider-1302699626";

    private static COSClient cosClient;

    static {
        ClassLoader classLoader = COSClient.class.getClassLoader();
        try (InputStream is = classLoader.getResourceAsStream("cos-client.properties")) {
            Properties properties = new Properties();
            properties.load(is);

            COSCredentials cred = new BasicCOSCredentials(properties.getProperty("cos.client.secretId"),
                    properties.getProperty("cos.client.secretKey"));

            ClientConfig clientConfig = new ClientConfig(new Region(properties.getProperty("cos.client.regionName")));

            cosClient = new COSClient(cred, clientConfig);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void simpleUpload(InputStream is, String fileName, long size) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(size);
        PutObjectResult putObjectResult = null;

        try {
            putObjectResult = cosClient.putObject(COSUtil.BUCKET_NAME, fileName, is, objectMetadata);
        } catch (CosClientException e) {
            throw new RuntimeException(MessageConstant.PIC_UPLOAD_FAIL);
        }

    }

}
