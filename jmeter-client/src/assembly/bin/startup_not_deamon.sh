#!/bin/bash
SERVICE_NAME="${project.artifactId}"
VERSION="${project.version}"
SERVICE_FULL_NAME=${SERVICE_NAME}-${VERSION}.jar

PRO_BIN_DIR=`dirname "$0"`
PRO_BASE_DIR=${PRO_BIN_DIR}/..
PRO_CONF_DIR=${PRO_BIN_DIR}/../conf
java -Xms256M -Xmx1024M -Dbase.dir=${PRO_BASE_DIR} -Dconf.dir=${PRO_CONF_DIR} -jar ${PRO_BASE_DIR}/lib/${SERVICE_FULL_NAME}