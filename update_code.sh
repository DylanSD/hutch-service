#!/bin/bash 

cd /home/pi/projects/relay_microservice
while (true) 
do
        sleep 1d
        svn up
        mvn clean install
done

