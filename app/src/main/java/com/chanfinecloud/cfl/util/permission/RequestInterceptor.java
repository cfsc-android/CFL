package com.chanfinecloud.cfl.util.permission;


import androidx.annotation.NonNull;

/**
 * 描述：
 *
 * @author Cuizhen
 * @date 2019/3/16
 */
public interface RequestInterceptor<T> {
    void intercept(@NonNull final T data, @NonNull final Executor executor);

    interface Executor {
        void execute();
        void cancel();
    }
}
