package com.t3rik.enums;

import lombok.Getter;

/**
 * @author t3rik
 * @date 2025/1/16 22:36
 */
@Getter
public enum OssTypeEnum {
    Default("minIOService"), MinIO("minIOService"), AliYun("aliYunService");

    private final String type;

    OssTypeEnum(String type) {
        this.type = type;
    }

}
