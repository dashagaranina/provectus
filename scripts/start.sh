#!/usr/bin/env bash
sh ./scripts/build.sh
docker-compose up --scale workerserver=$1