package com.tplink.seataperf.action.impl;

import com.tplink.seataperf.action.NoSqlDeviceUserAction;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NoSqlDeviceUserActionImpl implements NoSqlDeviceUserAction {

    Logger logger = LoggerFactory.getLogger(NoSqlDeviceUserActionImpl.class);


    @Override
    public boolean prepareAddDeviceUser(BusinessActionContext actionContext) {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            logger.error("failed to sleep.",e);
            return false;
        }
        return true;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        //do nothing
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            logger.error("failed to sleep.",e);
            return false;
        }
        return true;
    }
}
