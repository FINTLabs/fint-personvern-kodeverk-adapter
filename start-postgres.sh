#!/bin/bash

docker run --rm --name fint-personvern-kodeverk-adapter-postgres -p 5436:5432 -e POSTGRES_PASSWORD=password -e POSTGRES_DB=fint-personvern-kodeverk-adapter -d postgres