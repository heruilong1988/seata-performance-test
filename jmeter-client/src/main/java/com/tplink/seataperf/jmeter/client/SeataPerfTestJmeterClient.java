/*
 * Copyright (c) 2021, TP-Link Co.,Ltd.
 * Author: heruilong <heruilong@tp-link.com.cn>
 * Created: 2021/2/1
 */

package com.tplink.seataperf.jmeter.client;

import com.tplink.seataperf.action.service.DeviceUserTransactionService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.springframework.context.ApplicationContext;

public class SeataPerfTestJmeterClient extends AbstractJavaSamplerClient {

    ApplicationContext ctx;

    DeviceUserTransactionService deviceUserTransactionService;

    public static void main(String[] args) {
        SeataPerfTestJmeterClient client = new SeataPerfTestJmeterClient();
        client.setupTest(null);
        SampleResult sr = client.runTest(null);
        System.out.println(sr.isSuccessful());
    }

    @Override
    public void setupTest(JavaSamplerContext context) {
        super.setupTest(context);
        DubboInit.initApplicationContext();
        deviceUserTransactionService = (DeviceUserTransactionService) DubboInit.getInstance().getBean("deviceUserTx");
    }

    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        SampleResult sr = new SampleResult();
        sr.sampleStart();
        boolean r = deviceUserTransactionService.addDeviceUser();
        sr.setSuccessful(r);
        sr.sampleEnd();
        return sr;
    }
}
