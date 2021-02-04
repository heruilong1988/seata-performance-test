/*
 * Copyright (c) 2021, TP-Link Co.,Ltd.
 * Author: heruilong <heruilong@tp-link.com.cn>
 * Created: 2021/2/1
 */

package com.tplink.seataperf.jmeter.client;

import com.tplink.seataperf.action.service.DeviceUserTransactionService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class SeataPerfTestJmeterClient extends AbstractJavaSamplerClient {

    Logger logger = LoggerFactory.getLogger(SeataPerfTestJmeterClient.class);
    Logger rollbackLogger = LoggerFactory.getLogger("rollback");

    static AtomicLong id = new AtomicLong();

    ApplicationContext ctx;

    DeviceUserTransactionService deviceUserTransactionService;

    private String mode;
    private int rollbackRate;

    public static void main(String[] args) {
        SeataPerfTestJmeterClient client = new SeataPerfTestJmeterClient();
        client.setupTest(null);
        SampleResult sr = client.runTest(null);
        System.out.println(sr.isSuccessful());
    }

    @Override
    public Arguments getDefaultParameters() {
        Arguments arguments = new Arguments();
        arguments.addArgument("mode","normal"); //high
        arguments.addArgument("rollbackRate", "10000");
        return super.getDefaultParameters();
    }

    @Override
    public void setupTest(JavaSamplerContext context) {
        super.setupTest(context);
        DubboInit.initApplicationContext();
        deviceUserTransactionService = (DeviceUserTransactionService) DubboInit.getInstance().getBean("deviceUserTx");
        this.mode = context.getParameter("mode");
        this.rollbackRate = Integer.parseInt(context.getParameter("rollbackRate"));
    }

    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        SampleResult sr = new SampleResult();

        long reqId = id.getAndIncrement();

        boolean rollback = false;

        ThreadLocalRandom t= ThreadLocalRandom.current();
        if(t.nextInt(rollbackRate) == 1) {
            rollback = true;
            rollbackLogger.warn("rollback.reqId:{}",reqId);
        }

        sr.sampleStart();
        boolean r = deviceUserTransactionService.addDeviceUser(reqId,rollback,this.mode);
        sr.setSuccessful(r);
        sr.sampleEnd();
        return sr;
    }
}
