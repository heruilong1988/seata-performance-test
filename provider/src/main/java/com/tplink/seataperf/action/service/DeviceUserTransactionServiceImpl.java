package com.tplink.seataperf.action.service;


import com.tplink.seataperf.action.NoSqlDeviceUserAction;
import com.tplink.seataperf.action.SqlDeviceUserAction;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@DubboService(version = "1.0.0")
public class DeviceUserTransactionServiceImpl implements DeviceUserTransactionService {

    Logger logger = LoggerFactory.getLogger(DeviceUserTransactionServiceImpl.class);

    DistributedService distributedService;

    public DeviceUserTransactionServiceImpl(DistributedService distributedService) {
        this.distributedService = distributedService;
    }

    /**
     * 需要捕捉全局事务的异常，避免没有回复。
     */
    @Override
    public boolean addDeviceUser(long reqId, boolean needRollback, String businessMode) {
        logger.info("begin.addDeviceUser");
        try {
            return distributedService.addDeviceUserInternal(reqId, needRollback, businessMode);
        } catch (Exception e) {
            return false;
        }
    }



}
