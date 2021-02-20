package com.tplink.seataperf.action.impl;

import com.tplink.seataperf.action.SqlDeviceUserAction;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SqlDeviceUserActionImpl implements SqlDeviceUserAction {

    Logger logger = LoggerFactory.getLogger(SqlDeviceUserActionImpl.class);
    Logger rollbackLogger = LoggerFactory.getLogger("rollback");

    @Override
    public boolean prepareAddDeviceUser(BusinessActionContext actionContext, long reqId, boolean needRollback, String mode) {

        if(!mode.equals("normal")) {
            //模拟插入事务控制记录
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            logger.error("prepare failed to sleep.", e);
            return false;
        }

        if (needRollback) {
            return false;
        }

        return true;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        if(!"normal".equals(actionContext.getActionContext("mode"))){
            //把任务状态标记为完成
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                rollbackLogger.error("rollback failed to sleep.", e);
                return false;

            }
        }
        //do nothing
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        String reqId = actionContext.getActionContext("reqId").toString();
        rollbackLogger.warn("sql-rollback.reqId:{}", reqId);

        if(!"normal".equals(actionContext.getActionContext("mode"))){
            //把任务状态标记为rollback
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                rollbackLogger.error("rollback failed to sleep.", e);
                return false;
            }
        }

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            rollbackLogger.error("rollback failed to sleep.", e);
            return false;
        }
        return true;
    }
}
