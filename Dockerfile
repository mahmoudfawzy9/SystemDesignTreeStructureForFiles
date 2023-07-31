FROM postgres
WORKDIR /
ENV PGDATABASE=stc
ENV PGUSER=root
FROM openjdk:17
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/{jarName}.jar"]

