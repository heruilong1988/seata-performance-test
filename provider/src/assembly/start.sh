nohup java ${JAVA_OPTS} \
    -Dbase.dir=${PRO_BASE_DIR} -Dconf.dir=${PRO_CONF_DIR} \
    -Dconfig.define.print.enabled=${CONFIG_DEFINE_PRINT_ENABLED} \
    -Dfile.encoding=UTF-8 \
    ${SPRING_APPLICATION_OPTS} \
    -jar ${PRO_LIB_DIR}/${SERVICE_LIB_NAME} \
    > ${PRO_NOHUP_FILE} 2>&1 &