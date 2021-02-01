package com.tplink.seataperf.action.impl;

import com.tplink.seataperf.action.SqlDeviceUserAction;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SqlDeviceUserActionImpl  implements SqlDeviceUserAction {

    Logger logger = LoggerFactory.getLogger(SqlDeviceUserActionImpl.class);


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
            Thread.sleep(10);
        } catch (InterruptedException e) {
            logger.error("failed to sleep.",e);
            return false;
        }
        return true;
    }
}
