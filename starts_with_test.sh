#!/usr/bin/env bash
mvn -Pdocker clean package
docker-compose up --scale workerserver=$1