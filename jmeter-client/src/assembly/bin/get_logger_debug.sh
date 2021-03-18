#!/bin/bash

echo "cloud-deviceBasic operation tool - get logger method or parameter debug condition"
echo `date +"%Y-%m-%d %H:%M:%S"`

help()
{
    echo "Usage: ./get_logger_debug.sh <-m/-p> [<server_ip>:<port>] "
    echo "-m/-p: if input -m, return method log debug condition else return parameter log debug condition"
    echo "server_ip:port: if blank, localhost:8778 by default"
    exit 1
}

if [ $# -lt 1 -o $# -gt 2 ]; then
    help
elif [ $1 != "-m" -a $1 != "-p" ]; then
    help
fi


if [ $1 == "-m" -a $# -eq 2 ]; then
    SERVER_IP_PORT=$2
    /usr/bin/curl http://${SERVER_IP_PORT}/jolokia/exec/deviceUser:name=LoggerControllerJmx/getMethodMapping/
    exit 1
elif [ $1 == "-m" ]; then
    SERVER_IP_PORT=localhost:8778
    /usr/bin/curl http://${SERVER_IP_PORT}/jolokia/exec/deviceUser:name=LoggerControllerJmx/getMethodMapping/
    exit 1
fi

if [ $1 == "-p" -a $# -eq 2 ]; then
    SERVER_IP_PORT=$2
    /usr/bin/curl http://${SERVER_IP_PORT}/jolokia/exec/deviceUser:name=LoggerControllerJmx/getParameterMapping/
elif [ $1 == "-p" ]; then
    SERVER_IP_PORT=localhost:8778
    /usr/bin/curl http://${SERVER_IP_PORT}/jolokia/exec/deviceUser:name=LoggerControllerJmx/getParameterMapping/
fi
