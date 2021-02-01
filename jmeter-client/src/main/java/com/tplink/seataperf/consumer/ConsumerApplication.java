/*
 * Copyright (c) 2021, TP-Link Co.,Ltd.
 * Author: heruilong <heruilong@tp-link.com.cn>
 * Created: 2021/2/1
 */

package com.tplink.seataperf.consumer;

import com.tplink.seataperf.consumer.service.MyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ConsumerApplication.class,args);

        MyService myService = ctx.getBean(MyService.class);
        myService.doBusiness();
    }
}
