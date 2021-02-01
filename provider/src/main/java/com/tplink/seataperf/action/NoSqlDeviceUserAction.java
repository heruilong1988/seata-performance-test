package com.tplink.seataperf.action;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface NoSqlDeviceUserAction {

    /**
     * Prepare boolean.
     *
     * @param actionContext the action context
     * @return the boolean
     */
    @TwoPhaseBusinessAction(name = "prepareAddNoSqlDeviceUser" , commitMethod = "commit", rollbackMethod = "rollback")
    public boolean prepareAddDeviceUser(BusinessActionContext actionContext);
    /**
     * Commit boolean.
     *
     * @param actionContext the action context
     * @return the boolean
     */
    public boolean commit(BusinessActionContext actionContext);

    /**
     * Rollback boolean.
     *
     * @param actionContext the action context
     * @return the boolean
     */
    public boolean rollback(BusinessActionContext actionContext);
}
