package com.t3rik;

import lombok.Getter;

/**
 * @author t3rik
 * @date 2025/1/16 22:36
 */
@Getter
public enum OSSTypeEnum {
    Default("default"), MinIO("minIO"), AliYun("aliYun");

    private final String type;

    OSSTypeEnum(String type) {
        this.type = type;
    }

}
