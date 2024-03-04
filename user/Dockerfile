FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
RUN mvn install -DskipTests
COPY user/target/*.jar user.jar
ENTRYPOINT ["java","-jar","/user.jar"]