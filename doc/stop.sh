#!/bin/bash


PRO_BASE_DIR=$(cd `dirname $0`; pwd)/..
cd ${PRO_BASE_DIR}
PRO_PID_FILE=bin/running.pid
MAX_STOP_TIME_S=60

if [ -f "${PRO_PID_FILE}" ]; then
    if [ `ps -ef | grep $(cat "${PRO_PID_FILE}") | grep "seata-server" | wc -l` -gt 0 ]; then
        PID=`cat ${PRO_PID_FILE}`
        kill ${PID}
        COUNT=0
        while [ `ps -ef | grep ${PID} |grep "seata-server" | wc -l` -gt 0 ]; do
            sleep 1
            COUNT=`expr ${COUNT} + 1`
            echo "${COUNT} SECOND(S) ELAPSED..."
            if [ ${COUNT} -gt ${MAX_STOP_TIME_S} ]; then
                echo "kill -9 seata-server"
                kill -9 ${PID}
                break
            fi
        done

        rm ${PRO_PID_FILE}
        echo "seata-server STOPPED"
        exit 0
    fi
fi

echo "NO INSTANCE OF seata-server IS RUNNING"