#!/bin/sh

echo "The application will start in ${JHIPSTER_SLEEP}s..." && sleep ${JHIPSTER_SLEEP}
exec java ${JAVA_OPTS} -noverify -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -Djavax.net.ssl.trustStore=/cacerts -Djavax.net.ssl.trustStorePassword=changeit -cp /app/resources/:/app/classes/:/app/libs/* "sat.gob.mx.agsc.RfcrfApp"  "$@"
