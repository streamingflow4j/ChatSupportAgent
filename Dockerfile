FROM openjdk:17-oracle

RUN mkdir -p /app

VOLUME /app

ENV JAVA_OPTS="-Xms64m -Xmx512m -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m -Dfile.encoding=UTF-8"
ENV JAR_NAME="ChatSupportAgent-0.0.1-SNAPSHOT.jar"

COPY ./target/${JAR_NAME} /app

RUN mkdir -p /files/
VOLUME files/
COPY ./target/classes/files /files

EXPOSE 8185
EXPOSE 11434
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app/${JAR_NAME}" ]