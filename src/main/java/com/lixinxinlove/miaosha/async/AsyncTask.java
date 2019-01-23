package com.lixinxinlove.miaosha.async;


import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class AsyncTask {

    @Async
    public Future<Boolean> doTask1() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread.sleep(1000);
        long end = System.currentTimeMillis();
        System.out.println("任务1：" + (end - start) + "ms");
        return new AsyncResult<>(true);
    }

    @Async
    public Future<Boolean> doTask2() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread.sleep(1000);
        long end = System.currentTimeMillis();
        System.out.println("任务2：" + (end - start) + "ms");
        return new AsyncResult<>(true);
    }

    @Async
    public Future<Boolean> doTask3() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread.sleep(1000);
        long end = System.currentTimeMillis();
        System.out.println("任务3：" + (end - start) + "ms");
        return new AsyncResult<>(true);
    }

}
