set SERVICE_NAME=${project.artifactId}
set VERSION=${project.version}
java -javaagent:..\lib\jolokia-jvm-1.3.1-agent.jar=port=50250,host=0.0.0.0 -Xms256M -Xmx1024M -jar -Dbase.dir=.. -Dconf.dir=..\conf ..\lib\%SERVICE_NAME%-%VERSION%.jar