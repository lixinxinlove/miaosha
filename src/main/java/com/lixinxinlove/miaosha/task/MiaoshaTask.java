package com.lixinxinlove.miaosha.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

//@Component
public class MiaoshaTask {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

   // @Scheduled(fixedRate = 3000)
    public void re() {
        System.out.println("现在时间：" + dateFormat.format(new Date()));
    }


}
