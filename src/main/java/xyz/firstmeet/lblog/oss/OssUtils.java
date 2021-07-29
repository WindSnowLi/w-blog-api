package xyz.firstmeet.lblog.oss;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import xyz.firstmeet.lblog.utils.CodeUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class OssUtils {
    // Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
    private String endpoint;
    // AccessKey ID和AccessKey Secret。
    private String accessKeyId;
    private String accessKeySecret;
    // 填写Bucket名称。
    private String bucketName;
    // 回调服务器地址
    private String callbackUrl;

    /**
     * 获取上传签名Url
     *
     * @param objectName     不包含Bucket名称在内的Object完整路径
     * @param callbackParams 回调请求参数 示例：    &token=token
     * @param callBackStatus 是否回调
     * @return {host: 上传服务的url, urlParams: 上传签名Url对象, GetUrl: Get请求Url}
     */
    public HashMap<String, Object> getUploadUrl(String objectName, String callbackParams, boolean callBackStatus) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        HashMap<String, Object> respMap = new HashMap<>();
        try {
            Date date = new Date();
            date.setTime(date.getTime() + 60 * 60 * 1000);
            //GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.PUT);
            //request.setExpiration(date);
            //限速处理1MB/S
            //request.setTrafficLimit(1024 * 1024 * 8);
            // PostObject请求最大可支持的文件大小为5 GB，即CONTENT_LENGTH_RANGE为5*1024*1024*1024。
            // 这里用2MB
            PolicyConditions policyConditions = new PolicyConditions();
            policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1024 * 1024 * 2);
            policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, objectName);

            String postPolicy = ossClient.generatePostPolicy(date, policyConditions);
            //String postPolicy = ossClient.generatePostPolicy(request);
            byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            HashMap<String, String> urlParams = new HashMap<>();

            urlParams.put("OSSAccessKeyId", accessKeyId);
            urlParams.put("policy", encodedPolicy);
            urlParams.put("signature", postSignature);
            urlParams.put("key", objectName);
            urlParams.put("success_action_status", "200");
            //urlParams.put("expire", String.valueOf(expireEndTime / 1000));

            if (callBackStatus) {
                JSONObject jasonCallback = new JSONObject();
                jasonCallback.put("callbackUrl", callbackUrl);
                jasonCallback.put("callbackBody", "fileName=" + objectName + callbackParams);
                jasonCallback.put("callbackBodyType", "application/x-www-form-urlencoded");
                String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());
                urlParams.put("callback", base64CallbackBody);
            }
            respMap.put("urlParams", urlParams);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            ossClient.shutdown();
        }
        //用于使用的url
        respMap.put("GetUrl", "https://" + bucketName + "." + endpoint + "/" + objectName);
        //上传服务的url
        respMap.put("host", "https://" + bucketName + "." + endpoint);
        ossClient.shutdown();
        return respMap;
    }

    /**
     * 获取上传签名Url
     *
     * @param objectName     不包含Bucket名称在内的Object完整路径
     * @param callBackStatus 是否回调
     * @return {urlParams: 上传签名Url, GetUrl: Get请求Url}
     */
    public HashMap<String, Object> getUploadUrl(String objectName, boolean callBackStatus) {
        return getUploadUrl(objectName, "", callBackStatus);
    }

    /**
     * 获取GET方法访问的签名URL
     *
     * @param objectName 不包含Bucket名称在内的Object完整路径
     * @param second     Url过期时间，单位秒
     * @return GET方法请求Url
     */
    public String getMethodUrl(String objectName, long second) {
        // 设置URL过期时间，单位秒。
        Date expiration = new Date(new Date().getTime() + second * 1000);
        // 从STS服务获取临时访问凭证后，您可以通过临时访问密钥和安全令牌生成OSSClient。
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        URL url = ossClient.generatePresignedUrl(bucketName, objectName, expiration);
        ossClient.shutdown();
        return url.toString();
    }

    /**
     * 获取GET方法访问的签名URL，过期时间一小时
     *
     * @param objectName 不包含Bucket名称在内的Object完整路径
     * @return GET方法请求Url
     */
    public String getMethodUrl(String objectName) {
        return getMethodUrl(objectName, 3600);
    }

    /**
     * 通过签名Url上传文件
     *
     * @param signedUrl 签名Url
     * @param filePath  文件路径
     */
    public void uploadBySignedUrl(String signedUrl, String filePath, Map<String, String> customHeaders) throws FileNotFoundException, MalformedURLException {
        URL url = new URL(signedUrl);
        // 使用签名URL发送请求。
        // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。
        File f = new File(filePath);
        FileInputStream fin = new FileInputStream(f);
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 添加PutObject请求头。
        ossClient.putObject(url, fin, f.length(), customHeaders);
    }

    /**
     * 删除文件
     *
     * @param objectName 文件路径
     */
    public void deleteObject(String objectName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 删除文件。
        ossClient.deleteObject(this.bucketName, objectName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 获取public key
     *
     * @param url 请求url
     * @return 密钥结果
     */
    public String executeGet(String url) {
        BufferedReader in = null;

        String content = null;
        try {
            // 定义HttpClient
            HttpClient client = HttpClientBuilder.create().build();
            // 实例化HTTP方法
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder sb = new StringBuilder();
            String line;
            String nl = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line).append(nl);
            }
            in.close();
            content = sb.toString();
        } catch (Exception ignored) {
        } finally {
            if (in != null) {
                try {
                    // 最后要关闭BufferedReader
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }


    /**
     * 验证上传回调的Request
     *
     * @param header          上传回调请求头
     * @param ossCallbackBody 上传回调请求参数
     * @return 验证结果
     * @throws NumberFormatException 请求参数异常
     */
    public boolean verifyOssCallbackRequest(Map<String, String> header, String ossCallbackBody)
            throws NumberFormatException {
        String autorizationInput = header.get("authorization");
        String pubKeyInput = header.get("x-oss-pub-key-url");
        byte[] authorization = BinaryUtil.fromBase64String(autorizationInput);
        byte[] pubKey = BinaryUtil.fromBase64String(pubKeyInput);
        String pubKeyAddr = new String(pubKey);
        if (!pubKeyAddr.startsWith("http://gosspublic.alicdn.com/")
                && !pubKeyAddr.startsWith("https://gosspublic.alicdn.com/")) {
            return false;
        }
        String retString = executeGet(pubKeyAddr);
        retString = retString.replace("-----BEGIN PUBLIC KEY-----", "");
        retString = retString.replace("-----END PUBLIC KEY-----", "");
        String uri = header.get("uri");
        String authStr = java.net.URLDecoder.decode(uri, StandardCharsets.UTF_8);
        if (ossCallbackBody != null && !"".equals(ossCallbackBody)) {
            authStr += "?" + ossCallbackBody;
        }
        authStr += "\n" + ossCallbackBody;
        return CodeUtils.rsaCheck(authStr, authorization, retString);
    }
}
