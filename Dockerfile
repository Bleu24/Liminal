# Stage 1: Build the application
FROM eclipse-temurin:20-jdk-jammy AS build

# Set the working directory
WORKDIR /build

# Copy the Maven wrapper files and give permissions
COPY mvnw .
COPY .mvn .mvn
RUN chmod +x mvnw

# Copy the pom.xml files for dependency resolution
COPY pom.xml .
COPY common/pom.xml common/pom.xml
COPY empldata/pom.xml empldata/pom.xml
COPY sbemplms/pom.xml sbemplms/pom.xml

# Download the dependencies
RUN ./mvnw dependency:go-offline -B

# Copy the entire source code
COPY . .

# Build the entire project
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:20-jre-jammy AS final

# Set the working directory
WORKDIR /app

# Copy the built JAR of the main module you want to run
COPY --from=build /build/sbemplms/target/sbemplms-1.0-SNAPSHOT.jar sbemplms-1.0-SNAPSHOT.jar

# Expose the application port (adjust as needed)
EXPOSE 8080

# Run the main module
CMD ["java", "-jar", "sbemplms-1.0-SNAPSHOT.jar"]
