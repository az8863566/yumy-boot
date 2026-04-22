# ---- 构建阶段 ----
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /build
COPY pom.xml .
COPY yumy-common/pom.xml yumy-common/pom.xml
COPY yumy-framework/pom.xml yumy-framework/pom.xml
COPY yumy-business/pom.xml yumy-business/pom.xml
COPY yumy-admin/pom.xml yumy-admin/pom.xml
COPY yumy-toc/pom.xml yumy-toc/pom.xml
COPY yumy-bootstrap/pom.xml yumy-bootstrap/pom.xml
RUN mvn dependency:go-offline -B
COPY . .
RUN mvn clean package -Dmaven.test.skip=true -B

# ---- 运行阶段 ----
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /build/yumy-bootstrap/target/yumy-bootstrap-1.0.0-SNAPSHOT.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
