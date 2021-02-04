/*
 * Copyright (c) 2021, TP-Link Co.,Ltd.
 * Author: heruilong <heruilong@tp-link.com.cn>
 * Created: 2021/2/1
 */

package com.tplink.seataperf.consumer.service;

import com.tplink.seataperf.action.service.DeviceUserTransactionService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Component
public class MyService {

    @DubboReference(version = "1.0.0")
    DeviceUserTransactionService deviceUserTransactionService;

    public void doBusiness() {
        boolean result = deviceUserTransactionService.addDeviceUser(0, true);
        System.out.println("result: " + result);
    }
}
