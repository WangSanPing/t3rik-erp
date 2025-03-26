package com.t3rik.common.mybatis.plugin.marking;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * sql染色标记
 *
 * @author t3rik
 * @date 2025/3/25 21:52
 */
@Component
@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}))
public class SQLMarkingPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // long start = System.nanoTime();  // 记录起始时间

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        // **获取真正的 StatementHandler**
        statementHandler = getRealTarget(statementHandler);
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);

        // **获取 delegate**
        if (metaObject.hasGetter("delegate")) {
            statementHandler = (StatementHandler) metaObject.getValue("delegate");
            metaObject = SystemMetaObject.forObject(statementHandler);
        }
        // **获取 mappedStatement**
        if (!metaObject.hasGetter("mappedStatement")) {
            return invocation.proceed();
        }
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("mappedStatement");
        // 获取id
        String statementId = mappedStatement.getId();

        // 获取 BoundSql（SQL 语句对象）
        BoundSql boundSql = statementHandler.getBoundSql();
        String originalSql = boundSql.getSql();
        // 构造带有注释的新 SQL
        String markedSql = originalSql + " \n" + "/* Executed by: " + statementId;
        String sqlMarking = SQLMarkingThreadLocal.getSQLMarking();
        if (StringUtils.isNotBlank(sqlMarking)) {
            markedSql += ", 自定义变量: " + sqlMarking;
            SQLMarkingThreadLocal.removeSQLMarking();
        }
        markedSql += " */\n";
        // 修改 BoundSql 的 SQL 语句
        Field sqlField = BoundSql.class.getDeclaredField("sql");
        sqlField.setAccessible(true);
        sqlField.set(boundSql, markedSql);

        // long end = System.nanoTime();  // 记录结束时间
        // long duration = end - start;   // 计算耗时（纳秒）

        // System.out.println("SQLMarkingPlugin 耗时: " + (duration / 1_000_000.0) + " ms");


        return invocation.proceed();
    }


    /**
     * 获取真实的 StatementHandler
     */
    private StatementHandler getRealTarget(Object proxy) {
        while (Proxy.isProxyClass(proxy.getClass())) {
            InvocationHandler handler = Proxy.getInvocationHandler(proxy);
            MetaObject metaObject = SystemMetaObject.forObject(handler);
            if (metaObject.hasGetter("target")) {
                proxy = metaObject.getValue("target");
            } else {
                break;
            }
        }
        return (StatementHandler) proxy;
    }
}
