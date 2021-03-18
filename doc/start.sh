#!/bin/bash

PRO_BASE_DIR=$(cd `dirname $0`; pwd)/..
cd ${PRO_BASE_DIR}
PRO_CONF_DIR=conf
PRO_LIB_DIR=lib
PRO_LOGS_DIR=~/logs/seata
PRO_PID_FILE=bin/running.pid
PRO_NOHUP_FILE=${PRO_LOGS_DIR}/seata-server.8091.all.log

START_SUCCESS_OUTPUT="Server started"
MAX_START_TIME_S=60

if [ -f "${PRO_PID_FILE}" ]; then
    if [ `ps -ef | grep $(cat "${PRO_PID_FILE}") | grep java | grep "seata" | wc -l` -gt 0 ]; then

        echo "seater-server is running, PID:$(cat "${PRO_PID_FILE}")"
        exit 0
    fi
fi

if [ -f "${PRO_NOHUP_FILE}" ]; then
    mv ${PRO_NOHUP_FILE} ${PRO_NOHUP_FILE}.bak
fi

nohup ./bin/seata-server.sh -m db > ${PRO_NOHUP_FILE} 2>&1 &

if [ $? -eq 0 ]; then
    if /bin/echo -n $! > "${PRO_PID_FILE}"; then
        echo "SLEEP 3 SECONDS THEN CHECK PID" && sleep 3
        if [ `ps -ef | grep $(cat "$PRO_PID_FILE") | grep "seata-server" | wc -l` -gt 0 ]; then
            COUNT=0
            while [ `cat "${PRO_NOHUP_FILE}" | grep "${START_SUCCESS_OUTPUT}" | wc -l` -eq 0 ]; do
                sleep 1
                COUNT=`expr ${COUNT} + 1`
                echo "${COUNT} SECOND(S) ELAPSED..."

                if [ ${COUNT} -eq ${MAX_START_TIME_S} ]; then
                    break
                fi
            done

            if [ ${COUNT} -eq ${MAX_START_TIME_S} ]; then
                echo "FAILED TO START ${SERVICE_FULL_NAME} AFTER ${MAX_START_TIME_S} SECONDS"
                kill -9 `cat ${PRO_PID_FILE}`
                rm ${PRO_PID_FILE}
                exit 1
            else
                cat ${PRO_NOHUP_FILE}
                echo ""
                echo "${SERVICE_FULL_NAME} STARTED, PID:$(cat "${PRO_PID_FILE}")"
            fi
        else
            echo "FAILED TO START ${SERVICE_FULL_NAME}"
            exit 1
        fi
    else
        echo "FAILED TO WRITE PID FILE"
        exit 1
    fi
else
    echo "${SERVICE_FULL_NAME} DID NOT START"
    exit 1
fi