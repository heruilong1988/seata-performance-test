#!/bin/bash

echo "cloud-deviceBasic operation tool - set particular method's or parameter's logger level debug."echo `date +"%Y-%m-%d %H:%M:%S"`

help()
{
    echo "Usage: ./set_logger_debug.sh <-m/-p> [<server_ip>:<port>] <methodName/parameterName> <parameterValue>"
    echo "-m/-p: if input -m, set method log debug condition else set parameter log debug condition"
    echo "server_ip:port: if blank, localhost:8778 by default"
    echo "methodName/parameterName: method name(-m) or parameter name(-p)"
    echo "parameterValue: parameter value(-p)"
    exit 1
}

if [[ $1 != "-m" && $1 != "-p" ]]; then
    help
elif [[ $1 == "-m" ]] && [[ $# -lt 2 || $# -gt 3 ]]; then
    help
elif [[ $1 == "-p" ]] && [[ $# -lt 3 || $# -gt 4 ]]; then
    help
fi

if [[ $1 == "-m" && $# -eq 3 ]]; then
    SERVER_IP_PORT=$2
    METHOD_NAME=$3
    /usr/bin/curl http://${SERVER_IP_PORT}/jolokia/exec/deviceUser:name=LoggerControllerJmx/putMethodMapping/${METHOD_NAME}
    exit 1
elif [[ $1 == "-m" ]]; then
    SERVER_IP_PORT=localhost:8778
    METHOD_NAME=$2
    /usr/bin/curl http://${SERVER_IP_PORT}/jolokia/exec/deviceUser:name=LoggerControllerJmx/putMethodMapping/${METHOD_NAME}
    exit 1
fi

if [[ $1 == "-p" && $# -eq 4 ]]; then
    SERVER_IP_PORT=$2
    PARA_NAME=$3
    PARA_VALUE=$4
    /usr/bin/curl http://${SERVER_IP_PORT}/jolokia/exec/deviceUser:name=LoggerControllerJmx/putParameterMapping/${PARA_NAME}/${PARA_VALUE}
elif [[ $1 == "-p" ]]; then
    SERVER_IP_PORT=localhost:8778
    PARA_NAME=$2
    PARA_VALUE=$3
    /usr/bin/curl http://${SERVER_IP_PORT}/jolokia/exec/deviceUser:name=LoggerControllerJmx/putParameterMapping/${PARA_NAME}/${PARA_VALUE}
fi

