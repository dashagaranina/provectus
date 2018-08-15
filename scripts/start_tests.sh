#!/usr/bin/env bash
sh ./scripts/build_tests.sh
docker-compose up --scale workerserver=$1