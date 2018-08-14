#!/usr/bin/env bash
mvn -Pdocker -DskipTests=true clean package
docker-compose up --scale workerserver=$1