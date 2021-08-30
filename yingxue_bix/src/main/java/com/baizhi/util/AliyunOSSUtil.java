package com.baizhi.util;


import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

public class AliyunOSSUtil {

    // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
    private static String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
    private static String accessKeyId = "LTAI5tKzK93TUR27aDc9Q6vr";
    private static String accessKeySecret = "VVZpqRT6RizLJ560txs9nvJNTyIqnl";

    /**
     * 将文件以字节数组的形式上传至阿里云
     *  参数：
     * @param bucketName（String）  存储空间名
     * @param fileName（String）    文件名   目录名/文件名
     * @param headImg（MultipartFile） MultipartFile类型的文件
     * */
    public static void uploadfileBytes(String bucketName, String fileName, MultipartFile headImg){

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret);

        // 填写Byte数组。

        byte[] bytes = new byte[0];
        try {
            bytes = headImg.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
        ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(bytes));

        // 关闭OSSClient。
        ossClient.shutdown();
    }


    /**
     * 将阿里云文件删除
     *  参数：
     * @param bucketName（String）  存储空间名  yingx-2103
     * @param fileName（String）    文件名   目录名/文件名  lalala.png
     * */
    public static void deleteFile(String bucketName, String fileName){

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件或目录。如果要删除目录，目录必须为空。
        ossClient.deleteObject(bucketName, fileName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 截取视频封面
     *  参数：
     * @param bucketName（String）  存储空间名  yingx-2103
     * @param fileName（String）    要截取封面的视频名   目录名/文件名
     * */
    public static URL videoInterceptCover(String bucketName,String fileName){

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 使用精确时间模式截取视频50s处的内容，输出为JPG格式的图片，宽度为800，高度为600。
        String style = "video/snapshot,t_1000,f_jpg,w_800,h_600";

        // 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, fileName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        //System.out.println(signedUrl);
        // 关闭OSSClient。
        ossClient.shutdown();

        return signedUrl;
    }


    /**
     * 将网络文件上传至阿里云
     *  参数：
     * @param bucketName（String）  存储空间名  yingx-2103
     * @param fileName（String）    要上传的图片名   目录名/文件名
     * @param netFilePath（String）   要上传文件的网络路径
     * */
    public static void uploadNetIO(String bucketName,String fileName,String netFilePath) throws IOException {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 填写网络流地址。
        InputStream inputStream = new URL(netFilePath).openStream();
        // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
        ossClient.putObject(bucketName, fileName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 截取视频封面
     *  参数：
     * @param bucketName（String）  存储空间名  yingx-2103
     * @param fileName（String）    要截取封面的视频名   目录名/文件名
     * @param coverName（String）    要保存的封面名      目录名/文件名
     * */
    public static void videoInterceptCoverUpload(String bucketName,String fileName,String coverName) throws IOException {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 使用精确时间模式截取视频50s处的内容，输出为JPG格式的图片，宽度为800，高度为600。
        String style = "video/snapshot,t_1000,f_jpg,w_800,h_600";

        // 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, fileName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        System.out.println(signedUrl);

        //上传封面
        //---------------
        // 填写网络流地址。
        InputStream inputStream = new URL(signedUrl.toString()).openStream();
        // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
        ossClient.putObject(bucketName, fileName, inputStream);
        //---------------

        // 关闭OSSClient。
        ossClient.shutdown();
    }

}
