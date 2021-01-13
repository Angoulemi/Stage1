package org.ds.health.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.exception.MultiObjectDeleteException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.ds.health.constant.MessageConstant;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public static List<String> listFile() {
        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        // 设置bucket名称
        listObjectsRequest.setBucketName(COSUtil.BUCKET_NAME);
        // deliter表示分隔符, 设置为/表示列出当前目录下的object, 设置为空表示列出所有的object
        listObjectsRequest.setDelimiter("/");
        // 设置最大遍历出多少个对象, 一次listobject最大支持1000
        listObjectsRequest.setMaxKeys(1000);
        ObjectListing objectListing = null;
        List<String> fileList;
        do {
            try {
                objectListing = cosClient.listObjects(listObjectsRequest);
            } catch (CosClientException ignored) {

            }

            // object summary表示所有列出的object列表
            List<COSObjectSummary> cosObjectSummaries = Objects.requireNonNull(objectListing).getObjectSummaries();
            fileList = new ArrayList<>(cosObjectSummaries.size());
            for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {
                // 文件的路径key
                String key = cosObjectSummary.getKey();
                fileList.add(key);
            }
            String nextMarker = objectListing.getNextMarker();
            listObjectsRequest.setMarker(nextMarker);
        } while (objectListing.isTruncated());
        return fileList;
    }

    public static void removeFiles(List<String> fileNames) {
        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(COSUtil.BUCKET_NAME);
         // 设置要删除的key列表, 最多一次删除1000个
        ArrayList<DeleteObjectsRequest.KeyVersion> keyList = new ArrayList<>();
        // 传入要删除的文件名
        for (String fileName : fileNames) {
            keyList.add(new DeleteObjectsRequest.KeyVersion(fileName));
        }
        deleteObjectsRequest.setKeys(keyList);
        // 批量删除文件
        try {
            DeleteObjectsResult deleteObjectsResult = cosClient.deleteObjects(deleteObjectsRequest);
            List<DeleteObjectsResult.DeletedObject> deleteObjectResultArray = deleteObjectsResult.getDeletedObjects();
        } catch (MultiObjectDeleteException mde) { // 如果部分删除成功部分失败, 返回MultiObjectDeleteException
            List<DeleteObjectsResult.DeletedObject> deleteObjects = mde.getDeletedObjects();
            List<MultiObjectDeleteException.DeleteError> deleteErrors = mde.getErrors();
        }
    }
}
