#!/bin/bash

echo "cloud-deviceBasic operation tool - remove particular method's or parameter's logger level debug."echo `date +"%Y-%m-%d %H:%M:%S"`

help()
{
    echo "Usage: ./remove_logger_debug.sh <-m/-p> [<server_ip>:<port>] <methodName/parameterName>"
    echo "-m/-p: if input -m, return method log debug condition else return parameter log debug condition"
    echo "server_ip:port: if blank, localhost:8778 by default"
    echo "methodName/parameterName: method name(-m) or parameter name(-p)"
    exit 1
}

if [ $# -lt 2 -o $# -gt 3 ]; then
    help
elif [ $1 != "-m" -a $1 != "-p" ]; then
    help
fi

if [ $1 == "-m" -a $# -eq 3 ]; then
    SERVER_IP_PORT=$2
    METHOD_NAME=$3
    /usr/bin/curl http://${SERVER_IP_PORT}/jolokia/exec/deviceUser:name=LoggerControllerJmx/removeMethodMapping/${METHOD_NAME}
    exit
elif [ $1 == "-m" ]; then
    SERVER_IP_PORT=localhost:8778
    METHOD_NAME=$2
    /usr/bin/curl http://${SERVER_IP_PORT}/jolokia/exec/deviceUser:name=LoggerControllerJmx/removeMethodMapping/${METHOD_NAME}
    exit
fi

if [ $1 == "-p" -a $# -eq 3 ]; then
    SERVER_IP_PORT=$2
    PARA_NAME=$3
    /usr/bin/curl http://${SERVER_IP_PORT}/jolokia/exec/deviceUser:name=LoggerControllerJmx/removeParameterMapping/${PARA_NAME}
elif [ $1 == "-p" ]; then
    SERVER_IP_PORT=localhost:8778
    PARA_NAME=$2
    /usr/bin/curl http://${SERVER_IP_PORT}/jolokia/exec/deviceUser:name=LoggerControllerJmx/removeParameterMapping/${PARA_NAME}
fi

