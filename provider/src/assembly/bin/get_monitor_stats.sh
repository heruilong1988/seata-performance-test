#!/bin/bash

echo "cloud-device operation tool - get monitor stats"
echo `date +"%Y-%m-%d %H:%M:%S"`

if [[ $1 && $1 != "-t" ]]; then
    echo "Usage: ./get_monitor_stats.sh [-t]"
    echo "-t, localhost:12278, if blank, localhost:8778 by default"
    exit 1
fi

SERVER_IP_PORT=localhost:8778

unset OPTIND
while getopts "t" opt;
do
    case ${opt} in
        t)
            SERVER_IP_PORT=localhost:12278
            ;;
        \?)
            echo "unknown argument"
            exit 1
            ;;
    esac
done

if [ -n "`which jq`" ]; then
    /usr/bin/curl http://${SERVER_IP_PORT}/jolokia/read/deviceUser:name=StatJmx/stat | jq .
else
    /usr/bin/curl http://${SERVER_IP_PORT}/jolokia/read/deviceUser:name=StatJmx/stat
fi
