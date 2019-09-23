package com.wqp.common.util.thread;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadSchedule {
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());

    public static void execute(Runnable r){
        threadPoolExecutor.execute(r);
    }

    public static boolean remove(Runnable r){
        return threadPoolExecutor.remove(r);
    }
}
