FROM java:8

ADD build/database.mv.db /usr/src/database.mv.db
ADD build/database.trace.db /usr/src/database.trace.db

ADD build/k-festival-0.0.1-SNAPSHOT.jar /usr/src/sample.jar
RUN chmod +x /usr/src/sample.jar


WORKDIR /usr/src

VOLUME /usr/src
EXPOSE 8080

CMD ["./sample.jar"]
