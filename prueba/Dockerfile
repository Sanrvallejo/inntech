#Imagen
FROM eclipse-temurin:17.0.15_6-jdk

#Exponer puerto
EXPOSE 8080

#Directorio raiz
WORKDIR /root

#Copiar y pegar archivos
COPY ./pom.xml /root
COPY ./.mvn /root/.mvn
COPY ./mvnw /root

#Dependencias
RUN ./mvnw dependency:go-offline

#Codigo fuente src
COPY ./src /root/src

#Contruir app
RUN ./mvnw clean install -DskipTests

#Levantar app
ENTRYPOINT ["java","-jar","/root/target/prueba-0.0.1-SNAPSHOT.jar"]

