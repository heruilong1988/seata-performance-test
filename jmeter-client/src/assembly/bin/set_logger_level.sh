#!/bin/bash

echo "cloud-device operation tool - set logger level"
echo `date +"%Y-%m-%d %H:%M:%S"`

if [ $# -lt 2 -o $# -gt 3 ]; then
    echo "Usage: ./set_logger_level.sh [<server_ip>:<port>] <package/class> <logger_level>"
    echo "server_ip:port: if blank, localhost:8778 by default"
    echo "package/class: full path of package or class"
    echo "logger_level: debug/info/warn/error"
    exit 1
fi

if [ $# -eq 3 ]; then
    SERVER_IP_PORT=$1
    PACKAGE_CLASS_PATH=$2
    LOGGER_LEVEL=$3
else
    SERVER_IP_PORT=localhost:8778
    PACKAGE_CLASS_PATH=$1
    LOGGER_LEVEL=$2
fi

/usr/bin/curl http://${SERVER_IP_PORT}/jolokia/exec/com.tplink.cloud.common.utils.jmx:type=LoggerModification/setLoggerLevel/${PACKAGE_CLASS_PATH}/${LOGGER_LEVEL}
