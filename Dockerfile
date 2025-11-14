# ----- Stage 1: Build Stage -----
# 'build' stage එකට Java 17 (Maven) image එකක් use කරනවා
FROM maven:3.9.6-eclipse-temurin-17-focal AS build

# App එක 'build' කරන්න work directory එකක් හදනවා
WORKDIR /app

# 'pom.xml' file එක copy කරලා, dependencies ටික download කරනවා
COPY pom.xml .
RUN mvn dependency:go-offline

# Project එකේ අනිත් source code files ටික copy කරනවා
COPY src ./src

# Application එක 'build' කරලා, 'jar' file එක හදනවා
RUN mvn clean install -DskipTests

# ----- Stage 2: Run Stage -----
# 'run' stage එකට, app එක run කරන්න විතරක්
# Java 17 (JRE) තියෙන 'slim' (చిన్న) image එකක් use කරනවා
FROM eclipse-temurin:17-jre-focal

# Run කරන්න work directory එකක් හදනවා
WORKDIR /app

# 'build' stage එකේ හදපු 'jar' file එක
# මේ 'run' stage එකට copy කරගන්නවා
COPY --from=build /app/target/ingestion-service-0.0.1-SNAPSHOT.jar app.jar

# Docker container එක run වෙනකොට,
# 'app.jar' file එක run කරන්න කියලා command එක දෙනවා
ENTRYPOINT ["java", "-jar", "app.jar"]