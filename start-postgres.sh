#!/bin/bash

docker run --rm --name fint-personvern-adapter-postgres -p 5436:5432 -e POSTGRES_PASSWORD=password -e POSTGRES_DB=fint-personvern-adapter -d postgres