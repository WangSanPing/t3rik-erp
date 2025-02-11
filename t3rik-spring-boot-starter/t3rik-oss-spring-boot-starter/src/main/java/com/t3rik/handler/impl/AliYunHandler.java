package com.t3rik.handler.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.GetObjectRequest;
import com.t3rik.config.AliYunConfig;
import com.t3rik.config.OssProperties;
import com.t3rik.exception.T3rikOssException;
import com.t3rik.handler.IOSSHandler;
import com.t3rik.utils.CommonUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @author t3rik
 * @date 2025/1/18 16:53
 */
@Slf4j
@Service("aliYunService")
@Import(AliYunConfig.class)
public class AliYunHandler implements IOSSHandler {

    @Autowired(required = false)
    private OSS aliYunClient;
    @Resource
    private OssProperties ossProperties;

    /**
     * 上传文件
     *
     * @param fileName    文件名
     * @param inputStream 文件
     * @return oss访问路径
     */
    @Override
    public String uploadFile(String fileName, InputStream inputStream) {
        return this.uploadFileWithPrefix("", fileName, inputStream);
    }

    /**
     * 上传文件，保存后带oss前缀路径
     *
     * @param prefix      前缀文件夹
     * @param fileName    文件名
     * @param inputStream 文件
     * @return oss访问路径
     */
    @Override
    public String uploadFileWithPrefix(String prefix, String fileName, InputStream inputStream) {
        String filePath = CommonUtils.buildFilePath(prefix, fileName);
        try {
            aliYunClient.putObject(ossProperties.getBuket(), filePath, inputStream);
        } catch (Exception e) {
            throw new T3rikOssException("aliYun上传文件失败，请确认是否已经在配置文件中正确配置了minIO", e);
        }
        // 文件访问路径规则 https://BucketName.Endpoint/ObjectName
        return "https://" +
                ossProperties.getBuket() +
                "." +
                ossProperties.getEndPoint() +
                CommonUtils.separator +
                filePath;
    }

    /**
     * 删除文件
     *
     * @param url
     */
    @Override
    public void deleteFile(String url) {
        if (StringUtils.isBlank(url)) {
            throw new T3rikOssException("文件不能为空!");
        }
        try {
            String objectName = CommonUtils.buildUrlPath(url, ossProperties.getBuket(), ossProperties.getEndPoint());
            aliYunClient.deleteObject(ossProperties.getBuket(), objectName);
        } catch (Exception e) {
            throw new T3rikOssException("aliYun删除文件失败，请确认是否已经在配置文件中正确配置了aliYun", e);
        }
    }

    /**
     * 下载文件
     *
     * @param url 文件服务器存放地址
     * @return 返回值提示
     */

    @Override
    public void downLoadFile(String url, HttpServletResponse response) {
        try {
            String objectName = CommonUtils.buildUrlPath(url, ossProperties.getBuket(), ossProperties.getEndPoint());
            // 下载Object到本地文件，并保存到指定的本地路径中。如果指定的本地文件存在会覆盖，不存在则新建。
            // 如果未指定本地路径，则下载后的文件默认保存到示例程序所属项目对应本地路径中。
            aliYunClient.getObject(new GetObjectRequest(ossProperties.getBuket(), objectName));
        } catch (Exception e) {
            throw new T3rikOssException("aliYun下载文件失败，请确认是否已经在配置文件中正确配置了aliYun", e);
        }
    }
}
