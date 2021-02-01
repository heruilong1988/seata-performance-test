/*
 * Copyright (c) 2021, TP-Link Co.,Ltd.
 * Author: heruilong <heruilong@tp-link.com.cn>
 * Created: 2021/2/1
 */

package com.tplink.seataperf;

import com.tplink.seataperf.action.service.DeviceUserTransactionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ProviderApplicationStarter {

    public static void main(String[] args) {

       ApplicationContext ctx =  SpringApplication.run(ProviderApplicationStarter.class,args);
       //DeviceUserTransactionService deviceUserTransactionService = ctx.getBean(DeviceUserTransactionService.class);
       //deviceUserTransactionService.addDeviceUser();
    }

}
