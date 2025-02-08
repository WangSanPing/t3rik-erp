package com.t3rik.handler.impl;

import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectRequest;
import com.t3rik.config.HuaWeiObsConfig;
import com.t3rik.config.OssProperties;
import com.t3rik.handler.IOSSHandler;
import com.t3rik.utils.CommonUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @ClassName HuaWeiObsHandler
 * @Description:
 * @Author: 施子安
 * @CreateDate: 2025/2/8 19:57
 * @UpdateUser: 更新人
 * @UpdateDate: 2025/2/8 19:57
 * @UpdateRemark:
 * @Version: 1.0
 */
@Slf4j
@Service("huaWeiObsService")
@Import(HuaWeiObsConfig.class)
public class HuaWeiObsHandler implements IOSSHandler {

    @Resource
    private OssProperties ossProperties;

    @Autowired(required = false)
    private ObsClient obsClient;
    /**
     * 上传文件
     *
     * @param fileName    文件名
     * @param inputStream 文件
     * @return obs访问路径
     */
    @Override
    public String uploadFile(String fileName, InputStream inputStream) {
        return this.uploadFileWithPrefix("", fileName, inputStream);
    }

    /**
     * 上传文件，保存后带obs前缀路径
     *
     * @param prefix      前缀文件夹
     * @param fileName    文件名
     * @param inputStream 文件
     * @return obs访问路径
     */
    @Override
    public String uploadFileWithPrefix(String prefix, String fileName, InputStream inputStream) {
        String filePath = CommonUtils.builderFilePath(prefix, fileName);
        try {
           obsClient.putObject(new PutObjectRequest(ossProperties.getBuket(),filePath,inputStream));
        } catch (Exception e) {
            log.error("huaWeiObs，请确认是否已经在配置文件中正确配置了huaWeiObs,异常信息: {}", e.getMessage());
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
            throw new RuntimeException("文件不能为空!");
        }
        try {
            String prefix = ossProperties.getBuket() + "." + ossProperties.getEndPoint();
            int start = url.indexOf(prefix);
            String objectName = url.substring(start + prefix.length() + 1);
            obsClient.deleteObject(ossProperties.getBuket(), objectName);
        } catch (Exception e) {
            log.error("huaWeiObs删除文件失败，请确认是否已经在配置文件中正确配置了huaWeiObs,异常信息: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
