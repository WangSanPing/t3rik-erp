package com.t3rik.processor;

import com.t3rik.config.OssProperties;
import com.t3rik.handler.IOSSHandler;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

/**
 * OSS操作类
 *
 * @author t3rik
 * @date 2025/1/18 19:58
 */
@Service
public class OssProcessor {

    @Resource
    private Map<String, IOSSHandler> ossService;
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
        return ossService.get(ossProperties.getOssType().getType()).uploadFile(fileName, inputStream);
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
        return ossService.get(ossProperties.getOssType().getType()).uploadFileWithPrefix(prefix, fileName, inputStream);
    }

    /**
     * 删除文件
     */
    public void deleteFile(String url) {
        ossService.get(ossProperties.getOssType().getType()).deleteFile(url);
    }
}
