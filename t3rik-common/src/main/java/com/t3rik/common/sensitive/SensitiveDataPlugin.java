package com.t3rik.common.sensitive;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * mybatis数据脱敏插件
 *
 * @author t3rik
 * @date 2025/1/7 23:29
 */
@Component
@Intercepts(@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = Statement.class))
public class SensitiveDataPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        List<Object> records = (List<Object>) invocation.proceed();
        if (CollectionUtils.isEmpty(records)) {
            return records;
        }
        records.forEach(this::sensitiveHandler);
        return records;
    }

    private void sensitiveHandler(Object source) {
        MetaObject metaObject = SystemMetaObject.forObject(source);
        Class<?> sourceClass = source.getClass();
        sourceClass = sourceClass.getSuperclass();
        List<Field> fieldList = new ArrayList<>(Arrays.stream(sourceClass.getDeclaredFields()).toList());
        // 只多取一级父类
        Class<?> superclass = sourceClass.getSuperclass();
        if (superclass != null && superclass.getDeclaredFields().length > 0) {
            fieldList.addAll(Arrays.stream(superclass.getDeclaredFields()).toList());
        }
        fieldList.stream()
                .filter(field -> field.isAnnotationPresent(SensitiveData.class))
                .forEach(field -> doSensitive(metaObject, field));
    }

    private void doSensitive(MetaObject metaObject, Field field) {
        String name = field.getName();
        Object value = metaObject.getValue(name);
        if (value == null || metaObject.getGetterType(name) != String.class) {
            return;
        }
        SensitiveData annotation = field.getAnnotation(SensitiveData.class);
        SensitiveStrategyEnum strategy = annotation.strategy();
        String processor = strategy.getSensitiveData().processor((String) value);
        metaObject.setValue(name, processor);
    }
}
