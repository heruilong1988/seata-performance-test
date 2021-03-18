package com.tplink.seataperf.jmeter.client;/*
 * Copyright (c) 2021, TP-Link Co.,Ltd.
 * Author: heruilong <heruilong@tp-link.com.cn>
 * Created: 2021/2/1
 */


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

public class SeataPerfTestJmeterClient2 extends AbstractJavaSamplerClient {

    Logger logger = LoggerFactory.getLogger(SeataPerfTestJmeterClient2.class);
    Logger rollbackLogger = LoggerFactory.getLogger("rollback");

    static AtomicLong id = new AtomicLong();

    ApplicationContext ctx;

    DeviceUserTransactionService deviceUserTransactionService;

    private String mode;
    private int rollbackRate;
    private String zkAddress;

    public static void main2(String[] args) {
        System.out.println("a");
    }

    public static void main(String[] args) {
        SeataPerfTestJmeterClient2 client = new SeataPerfTestJmeterClient2();
        JavaSamplerContext context = new JavaSamplerContext(client.getDefaultParameters());
        client.setupTest(context);
        SampleResult sr = client.runTest(null);
        System.out.println(sr.isSuccessful());

        System.out.println("The seata-performance-test-jmeter-client service started");
    }

    @Override
    public Arguments getDefaultParameters() {
        Arguments arguments = new Arguments();
        arguments.addArgument("mode","normal"); //high，放悬挂
        arguments.addArgument("rollbackRate", "10000"); //万分之一的回滚率
        arguments.addArgument("zkAddress","zookeeper://172.29.153.22:2181?client=curator");
        return arguments;
    }

    @Override
    public void setupTest(JavaSamplerContext context) {
        super.setupTest(context);
        //DubboInit.initApplicationContext();
        this.mode = context.getParameter("mode");
        this.rollbackRate = Integer.parseInt(context.getParameter("rollbackRate"));
        this.zkAddress = context.getParameter("zkAddress");
        deviceUserTransactionService = (DeviceUserTransactionService) DubboInit.getInstance().getService(zkAddress);
        System.out.println("rollbackRate:" + rollbackRate);
        System.out.println("zkAddress:" + zkAddress);
        System.out.println("mode:" + mode);
    }

    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        SampleResult sr = new SampleResult();

        long reqId = id.getAndIncrement();

        boolean rollback = false;

        ThreadLocalRandom t= ThreadLocalRandom.current();
        if(t.nextInt(rollbackRate) == 1) {
            rollback = true;
            rollbackLogger.warn("mannually rollback.reqId:{}",reqId);
        }

        sr.sampleStart();
        try {
            boolean r = deviceUserTransactionService.addDeviceUser(reqId, rollback, this.mode);
            sr.setSuccessful(r);
            sr.sampleEnd();
        }catch (Exception e) {
            logger.error("error.", e);
            sr.setSuccessful(false);
            sr.sampleEnd();
        }

        return sr;
    }
}
