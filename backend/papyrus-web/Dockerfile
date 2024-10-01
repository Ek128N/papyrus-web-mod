FROM eclipse-temurin:17-jre
COPY target/papyrus-web-*.jar ./
RUN find ./ -name '*.jar' ! -name '*-sources.jar' -execdir mv {} papyrus-web.jar \;
EXPOSE 8080
RUN useradd myuser
USER myuser
ENTRYPOINT ["java","-jar","/papyrus-web.jar"]