FROM openjdk:8-stretch

WORKDIR /service

# Add gradle wrapper

ADD ./gradle /service/gradle
ADD ./gradlew /service/gradlew

# Add build file and resolve dependencies

ADD ./build.gradle /service/build.gradle
RUN ./gradlew resolveDependencies

# Add source code overlay
ADD . /service

# API port
EXPOSE 8080

# Build
RUN ./gradlew assemble

# Run
CMD ["./gradlew", "bootRun"]
