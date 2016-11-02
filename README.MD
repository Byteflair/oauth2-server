# Maven

Most of the project, if not all, is configured around Maven. You can run de server, or set it up running Maven commands. 

# Profiles

Maven is configured to use the Maven profile with the same name as the Spring profile used. So for example, if you invoke Maven with `-Dspring.profiles.active=dev`, Maven will apply the Maven profile named `dev` and Spring will also use a Spring profile named `dev`. To summarize, it is the `spring.profiles.active` property/environment variable that commands bot Spring and Maven profiles used.

# Project setup

This OAuth2 Server implementation uses JWT tokens. These tokens are signed with an RSA key pair. Before running the server you need to generate an RSA key pair in a key store.

The application is by default configured to use a keystore in `${project.build.path}`.
 
You can generate an initialized key store for development and testing purposes using the following command: `mvn -Dspring.profiles.active=true keytool:generateKeyPair`

# Running the server

After running the project setup, you can run the server with the following command: `mvn -Dspring.profiles.active=true spring-boot:run`
 
 
