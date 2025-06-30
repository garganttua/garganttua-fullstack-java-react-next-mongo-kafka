FROM alpine/java:22.0.2-jre
RUN addgroup -S spring && adduser -S spring -G spring
RUN mkdir -p /data/bus && chown -R spring:spring /data/bus
ENV DB_URL=mongodb://backend:backend@garganttua-showcase-mongo:27017/garganttua-showcase-site
ENV KAFKA_URL=garganttua-showcase-kafka:29092&maxPollRecords=1&enableAutoCommit=false&autoOffsetReset=latest&partitionsAutoScalling=true&allowAutoCreateTopics=true
USER spring:spring
ARG JAR_FILE=backend/target/showcase-site-backend-*.jar
ARG CONFIGURATION_FILE=backend/target/classes/application.properties
ARG CONTEXT_FILE_1=backend/target/classes/api-events.json
ARG CONTEXT_FILE_2=backend/target/classes/contact-request-mail-sender.json
COPY ${JAR_FILE} app.jar
COPY ${CONFIGURATION_FILE} application.properties
COPY ${CONTEXT_FILE_1} api-events.json
COPY ${CONTEXT_FILE_2} contact-request-mail-sender.json
ENTRYPOINT ["java","-jar","/app.jar", "--spring.config.location=optional:file:./application.properties"]
