# This tells Docker to use a very small version of the Java 8 base image
FROM java:8-jre-alpine

# Mounts a /tmp directory which Spring Boot uses for Tomcat working folders.
VOLUME /tmp

# Adds the jar file to the container with the given name "app.jar"
# Note the first parameter should match the build product from your gradle script.
ADD ./build/libs/*.jar app.jar

# Not really critical for a jar file, but docker creates all files with an
# unmodified time.  For static content files, they'd want a modification date.
RUN sh -c 'touch /app.jar'

# Defines the command and commandline properties to send when this image starts up.
# The "urandom" setting helps Tomcat to start faster.
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]