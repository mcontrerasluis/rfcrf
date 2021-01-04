#!/bin/sh

echo "The application will start in ${JHIPSTER_SLEEP}s..." && sleep ${JHIPSTER_SLEEP} && cp /Montserrat /usr/share/fonts/truetype/Montserrat/ -R
exec java ${JAVA_OPTS} -noverify -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "sat.gob.mx.agsc.RfcrfApp"  "$@"
