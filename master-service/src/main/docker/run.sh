#!/bin/sh

echo "********************************************************"
echo "Waiting for the database server to start on port $DATABASESERVER_PORT"
echo "********************************************************"
while ! nc -z database $DATABASESERVER_PORT; do sleep 3; done
echo ">>>>>>>>>>>> Database Server has started"

echo "********************************************************"
echo "Waiting for the eureka server to start on port $EUREKASERVER_PORT"
echo "********************************************************"
while ! nc -z eurekaserver $EUREKASERVER_PORT; do sleep 3; done
echo "******* Eureka Server has started"

echo "********************************************************"
echo "Waiting for the worker server to start on port $WORKERSERVER_PORT"
echo "********************************************************"
while ! nc -z workerserver $WORKERSERVER_PORT; do sleep 3; done
echo "******* Worker Server has started"

echo "********************************************************"
echo "Starting Master Server..."
echo "********************************************************"
java -Deureka.client.serviceUrl.defaultZone=$EUREKASERVER_URI -jar /usr/local/masterservice/@project.build.finalName@.jar