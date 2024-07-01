package com.t3rik.common.handler;

import java.util.function.Consumer;

/**
 * lambda异常帮助类
 *
 * @author t3rik
 * @date 2024/7/1 21:01
 */
public class LambdaExHelper {

    @FunctionalInterface
    public interface ThrowingConsumer<T> {
        void accept(T t) throws Exception;
    }

    public static <T> Consumer<T> wrap(ThrowingConsumer<T> throwingConsumer) {
        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
