/*
 * Copyright (c) 2021, TP-Link Co.,Ltd.
 * Author: heruilong <heruilong@tp-link.com.cn>
 * Created: 2021/2/1
 */

package com.tplink.seataperf.action.service;

import com.tplink.seataperf.action.NoSqlDeviceUserAction;
import com.tplink.seataperf.action.SqlDeviceUserAction;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Component;

@Component
public class DistributedService {

    SqlDeviceUserAction sqlDeviceUserAction;

    NoSqlDeviceUserAction noSqlDeviceUserAction;


    public DistributedService(SqlDeviceUserAction sqlDeviceUserAction,
        NoSqlDeviceUserAction noSqlDeviceUserAction) {
        this.sqlDeviceUserAction = sqlDeviceUserAction;
        this.noSqlDeviceUserAction = noSqlDeviceUserAction;
    }

    @GlobalTransactional
    public boolean addDeviceUserInternal() {
        boolean result1 = sqlDeviceUserAction.prepareAddDeviceUser(null);
        if (!result1) {
            throw new RuntimeException("sql failed");
        }

        boolean result2 = noSqlDeviceUserAction.prepareAddDeviceUser(null);
        if (!result2) {
            throw new RuntimeException("nosql failed");
        }
        return true;
    }
}
