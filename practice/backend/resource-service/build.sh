#! /bin/bash

# 文件目录不能以service后缀
SERVICE_NAME="$(basename $(pwd))""-service"

docker build -t $SERVICE_NAME .