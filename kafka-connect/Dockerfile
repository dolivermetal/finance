FROM confluentinc/cp-kafka-connect

RUN   confluent-hub install --no-prompt confluentinc/kafka-connect-jdbc:5.5.0 \
    && confluent-hub install --no-prompt confluentinc/connect-transforms:1.4.3 \
    && confluent-hub install --no-prompt jcustenborder/kafka-connect-transform-common:latest

COPY connector-jars/kconnect-jdbc-outbox-smt-1.0.3.jar  /usr/share/java/kafka-connect/

HEALTHCHECK --interval=15s --timeout=3s --start-period=20s --retries=3 \
  CMD [ "curl -sSL http://localhost:8083/connectors || exit 1" ]

ENTRYPOINT [ "/etc/confluent/docker/run" ]
