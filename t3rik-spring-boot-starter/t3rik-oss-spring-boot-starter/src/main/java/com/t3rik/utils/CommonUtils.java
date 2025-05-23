package com.t3rik.utils;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 公共常量
 *
 * @author t3rik
 * @date 2025/1/17 16:55
 */
public class CommonUtils {

    public final static String separator = "/";

    /**
     * 构建oss上的存储文件夹路径
     * 会返回两种类型
     * 有prefix: prefix/yyyy/mm/dd/file.jpg
     * 无prefix: yyyy/mm/dd/fileName.jpg
     *
     * @param prefix   可以在保存路径前加前置文件夹
     * @param fileName 文件名称
     * @return yyyy/mm/dd/fileName.jpg
     */
    public static String buildFilePath(String prefix, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        if (StringUtils.isNotBlank(prefix)) {
            stringBuilder.append(prefix).append(separator);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String todayStr = sdf.format(new Date());
        stringBuilder.append(todayStr).append(separator).append(fileName);
        byte[] bytes = stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 构建oss obs的前缀url
     *
     * @param url      下载地址
     * @param buket    桶名称
     * @param endPoint 地址
     * @return objectName 重新构建的文件地址
     */
    public static @NotNull String buildUrlPath(String url, String buket, String endPoint) {
        String prefix = buket + "." + endPoint;
        int start = url.indexOf(prefix);
        return url.substring(start + prefix.length() + 1);
    }
}
