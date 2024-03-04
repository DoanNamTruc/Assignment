FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY promotion/target/*.jar promotion.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/promotion.jar"]