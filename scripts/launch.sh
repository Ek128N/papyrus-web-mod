#!/bin/sh
java -Dpapyrus-web.graphql.websocket.allowed.origins="*" \
     -jar backend/papyrus-web-application/target/papyrus-web-application-*-SNAPSHOT.jar \
     --spring.datasource.url=jdbc:postgresql://localhost:5434/papyrus-web-db \
     --spring.datasource.username=dbuser \
     --spring.datasource.password=dbpwd \
     --spring.liquibase.change-log=classpath:db/changelog/papyrus-web.db.changelog.xml
