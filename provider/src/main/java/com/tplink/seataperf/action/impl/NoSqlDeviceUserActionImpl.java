package com.tplink.seataperf.action.impl;

import com.tplink.seataperf.action.NoSqlDeviceUserAction;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NoSqlDeviceUserActionImpl implements NoSqlDeviceUserAction {

    Logger logger = LoggerFactory.getLogger(NoSqlDeviceUserActionImpl.class);
    Logger rollbackLogger = LoggerFactory.getLogger("rollback");


    @Override
    public boolean prepareAddDeviceUser(BusinessActionContext actionContext,long reqId, boolean needRollback) {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            logger.error("prepare failed to sleep.",e);
            return false;
        }

        if(needRollback) {
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
        String reqId = actionContext.getActionContext("reqId").toString();
        rollbackLogger.warn("rollback.reqId:{}",reqId);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            rollbackLogger.error("rollback failed to sleep.",e);
            return false;
        }
        return true;
    }
}
