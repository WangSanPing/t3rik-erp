package com.t3rik.common.mybatis.plugin.marking;

/**
 * @author t3rik
 * @date 2025/3/26 15:04
 */
public class SQLMarkingThreadLocal {

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static String getSQLMarking() {
        return THREAD_LOCAL.get();
    }

    public static void setSQLMarking(String mark) {
        THREAD_LOCAL.set(mark);
    }

    public static void removeSQLMarking() {
        THREAD_LOCAL.remove();
    }
}
