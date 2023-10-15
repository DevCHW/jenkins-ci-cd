VOLUME /tmp

COPY module-api.jar api-server.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "api-server.jar"]
