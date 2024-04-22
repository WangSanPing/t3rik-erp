package com.t3rik.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.t3rik.common.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * mp自动填充
 *
 * @author t3rik
 * @date 2024/4/22 16:42
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 创建人
     */
    public static final String CREATE_USER = "createBy";
    /**
     * 修改人
     */
    public static final String UPDATE_USER = "updateBy";
    /**
     * 创建时间
     */
    public static final String CREATE_TIME = "createTime";
    /**
     * 修改时间
     */
    public static final String UPDATE_TIME = "updateTime";

    /**
     * 新增时执行
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Date now = new Date();
        String currentUserId = String.valueOf(SecurityUtils.getLoginUser().getUserId());
        // 创建人id
        if (ifHasField(metaObject, CREATE_USER)) {
            metaObject.setValue(CREATE_USER, currentUserId);
        }
        // 创建时间
        if (ifHasField(metaObject, CREATE_TIME)) {
            metaObject.setValue(CREATE_TIME, now);
        }
        // 修改人id
        if (ifHasField(metaObject, UPDATE_USER)) {
            metaObject.setValue(UPDATE_USER, currentUserId);
        }
        // 修改时间
        if (ifHasField(metaObject, UPDATE_TIME)) {
            metaObject.setValue(UPDATE_TIME, now);
        }
    }

    /**
     * 修改时执行
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        String currentUserId = String.valueOf(SecurityUtils.getLoginUser().getUserId());
        // 修改人id
        if (ifHasField(metaObject, UPDATE_USER)) {
            metaObject.setValue(UPDATE_USER, currentUserId);
        }
        // 修改时间
        if (ifHasField(metaObject, UPDATE_TIME)) {
            metaObject.setValue(UPDATE_TIME, new Date());
        }
    }

    /**
     * 校验是否存在字段
     *
     * @param metaObject metaObject
     * @param filedName  字段名
     * @return
     */
    private boolean ifHasField(MetaObject metaObject, String filedName) {
        if (metaObject.hasGetter(filedName)) {
            return true;
        } else if (metaObject.hasGetter("et." + filedName)) {
            return true;
        }
        return false;
    }
}
