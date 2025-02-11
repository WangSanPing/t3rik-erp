package com.t3rik.handler.impl;

import com.t3rik.config.MinIOConfig;
import com.t3rik.config.OssProperties;
import com.t3rik.exception.T3rikOssException;
import com.t3rik.handler.IOSSHandler;
import com.t3rik.utils.CommonUtils;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * minio
 *
 * @author t3rik
 * @date 2025/1/16 22:34
 */
@Slf4j
@Service("minIOService")
@Import(MinIOConfig.class)
public class MinIOHandler implements IOSSHandler {

    @Autowired(required = false)
    private MinioClient minioClient;
    @Resource
    private OssProperties ossProperties;

    /**
     * 上传文件
     *
     * @param fileName    文件名
     * @param inputStream 文件
     * @return oss访问路径
     */
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
    public String uploadFileWithPrefix(String prefix, String fileName, InputStream inputStream) {
        String filePath = CommonUtils.buildFilePath(prefix, fileName);
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)
                    .bucket(ossProperties.getBuket())
                    .stream(inputStream, inputStream.available(), -1)
                    .build();
            minioClient.putObject(putObjectArgs);
            // 拼接访问路径
            return ossProperties.getEndPoint() + CommonUtils.separator + ossProperties.getBuket() + CommonUtils.separator + filePath;
        } catch (Exception e) {
            throw new T3rikOssException("minIO上传文件失败，请确认是否已经在配置文件中正确配置了minIO", e);
        }
    }

    /**
     * 删除文件
     */
    public void deleteFile(String url) {
        if (StringUtils.isBlank(url)) {
            throw new T3rikOssException("文件不能为空!");
        }
        try {
            int start = url.indexOf(ossProperties.getBuket());
            String objectName = url.substring(start + ossProperties.getBuket().length());
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(ossProperties.getBuket())
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            throw new T3rikOssException("minIO删除文件失败，请确认是否已经在配置文件中正确配置了minIO", e);
        }
    }


    /**
     * 文件下载
     *
     * @param url 文件服务器存放地址
     * @return
     */
    @Override
    public void downLoadFile(String url, HttpServletResponse response) {
    }

}
