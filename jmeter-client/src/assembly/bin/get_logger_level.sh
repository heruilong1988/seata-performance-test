#!/bin/bash

echo "cloud-device operation tool - get logger level"
echo `date +"%Y-%m-%d %H:%M:%S"`

if [ $# -lt 1 -o $# -gt 2 ]; then
    echo "Usage: ./get_logger_level.sh [<server_ip>:<port>] <package/class>"
    echo "server_ip:port: if blank, localhost:8778 by default"
    echo "package/class: full path of package or class"
    exit 1
fi

if [ $# -eq 2 ]; then
    SERVER_IP_PORT=$1
    PACKAGE_CLASS_PATH=$2
else
    SERVER_IP_PORT=localhost:8778
    PACKAGE_CLASS_PATH=$1
fi

/usr/bin/curl http://${SERVER_IP_PORT}/jolokia/exec/com.tplink.cloud.common.utils.jmx:type=LoggerModification/getLoggerLevel/${PACKAGE_CLASS_PATH}
