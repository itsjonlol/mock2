# Use the Maven image as the base
FROM maven:3.9.9-eclipse-temurin-23 AS compiler
#FROM openjdk:23-jdk-oracle 
# Set up labels for metadata
# LABEL MAINTAINER="jonathan"
# LABEL DESCRIPTION="Mock1"
# LABEL name="mock1"

# Define application directory
ARG APP_DIR=/app 
WORKDIR ${APP_DIR}

# Copy project files into the image
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY src src

# Ensure the mvnw script has execution permissions
RUN chmod +x mvnw

# Build the application; might have to use mvn instead of ./mvnw
RUN mvn clean package -Dmaven.test.skip=true 

# Set the server port
ENV SERVER_PORT=4000

# Expose the port
EXPOSE ${SERVER_PORT}

# Run the application
ENTRYPOINT SERVER_PORT=${SERVER_PORT} java -jar target/eventmanagement-0.0.1-SNAPSHOT.jar

#Stage 2
FROM maven:3.9.9-eclipse-temurin-23

ARG DEPLOY_DIR=/code_folder

WORKDIR ${DEPLOY_DIR}

COPY --from=compiler /app/target/eventmanagement-0.0.1-SNAPSHOT.jar eventmanagement.jar

# Set the server port
ENV SERVER_PORT=4000

# Expose the port
EXPOSE ${SERVER_PORT}

# Run the application
ENTRYPOINT SERVER_PORT=${SERVER_PORT} java -jar eventmanagement.jar

#docker build -t itsjonlol/mock1:0.0.1 . 
#docker run -d -t -p 4000:4000 itsjonlol/mock1:0.0.1