#!/bin/sh
docker kill papyrus-web-postgres
docker run -p 5434:5432 --rm --name papyrus-web-postgres -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=dbpwd -e POSTGRES_DB=papyrus-web-db -d postgres
