#!/bin/bash

SERVICE_NAME="${project.artifactId}"
VERSION="${project.version}"
SERVICE_FULL_NAME=${SERVICE_NAME}-${VERSION}

PRO_BASE_DIR=$(cd `dirname $0`; pwd)/..
cd ${PRO_BASE_DIR}
PRO_LOGS_DIR=logs
PRO_PID_FILE=bin/running.pid
PRO_NOHUP_FILE=${PRO_LOGS_DIR}/nohup.out

START_SUCCESS_OUTPUT="The ${SERVICE_NAME} service started"

if [ -f "${PRO_PID_FILE}" ]; then
    PID=`cat ${PRO_PID_FILE}`
    if [ `ps -ef | grep ${PID} | grep java | grep ${SERVICE_FULL_NAME}| wc -l` -gt 0 ]; then
        echo "FOUND INSTANCE OF ${SERVICE_FULL_NAME} EXISTS, PID:${PID}"
        if [ `cat "${PRO_NOHUP_FILE}" | grep "${START_SUCCESS_OUTPUT}" | wc -l` -gt 0 ]; then
            echo "${SERVICE_FULL_NAME} IS RUNNING..."
            exit 0
        else
            echo "NOT FOUND THE SUCCESS OUTPUT '${START_SUCCESS_OUTPUT}' IN ${PRO_NOHUP_FILE}"
        fi
    fi
fi

echo "NO INSTANCE OF ${SERVICE_FULL_NAME} IS RUNNING"
exit 1
