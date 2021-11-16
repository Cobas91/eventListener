FROM openjdk:16

ENV ENVIRONMENT=prod

MAINTAINER Stephan Gärtner <stephan.gaertner72@outlook.de>

EXPOSE 8080

ADD backend/target/app.jar app.jar

CMD [ "sh", "-c", "java -Dserver.port=$PORT -jar /app.jar" ]