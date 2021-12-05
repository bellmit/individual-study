#! /bin/bash

SERVICE_NAME="$(basename $(pwd))""-service"
SERVICE_PORT=8082

docker rm -f $(docker ps -a | grep "$SERVICE_NAME" | awk '{print $1}')

docker run -d --name $SERVICE_NAME -p $SERVICE_PORT:$SERVICE_PORT -v /data/logs/backend:/home/gyw/logs -v /root/config-center:/root/config-center -v /root/data/web-crawler:/root/data $SERVICE_NAME
echo $! > image
