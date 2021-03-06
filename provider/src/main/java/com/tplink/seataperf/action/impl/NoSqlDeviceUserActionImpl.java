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
    public boolean prepareAddDeviceUser(BusinessActionContext actionContext,long reqId, boolean needRollback, String mode) {

        if(!"normal".equals(mode)) {
            //模拟插入事务控制记录
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error("prepare failed to sleep.",e);
                return false;
            }
        }

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
        if(!"normal".equals(actionContext.getActionContext("mode"))){
            //把任务状态标记为完成
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                rollbackLogger.error("rollback failed to sleep.", e);
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        String reqId = actionContext.getActionContext("reqId").toString();
        rollbackLogger.warn("nosql-rollback.reqId:{}",reqId);

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
            //回滚数据库
            Thread.sleep(10);
        } catch (InterruptedException e) {
            rollbackLogger.error("rollback failed to sleep.",e);
            return false;
        }
        return true;
    }
}
