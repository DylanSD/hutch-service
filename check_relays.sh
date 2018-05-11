#!/bin/bash 

while (true) 
do
        sleep 3m
        ANS=`curl -s -X GET http://localhost:8007/shouldLightBeOn`

        if [ "$ANS" == "true" ]
        then
            curl -s -X PUT http://localhost:8007/relays/2/ON
        else
            curl -s -X PUT http://localhost:8007/relays/2/OFF
        fi
done

