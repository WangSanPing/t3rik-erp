package com.t3rik.handler;

import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * @author t3rik
 * @date 2025/1/18 19:27
 */
public interface IOSSHandler {

    /**
     * 上传文件
     *
     * @param fileName    文件名
     * @param inputStream 文件
     * @return oss访问路径
     */
    String uploadFile(String fileName, InputStream inputStream);

    /**
     * 上传文件，保存后带oss前缀路径
     *
     * @param prefix      前缀文件夹
     * @param fileName    文件名
     * @param inputStream 文件
     * @return oss访问路径
     */
    String uploadFileWithPrefix(String prefix, String fileName, InputStream inputStream);

    /**
     * 删除文件
     */
    void deleteFile(String url);

    /**
     * 下载文件
     *
     * @param url 文件服务器存放地址
     */
    default void downLoadFile(String url, HttpServletResponse response) throws Exception {
    }
}
