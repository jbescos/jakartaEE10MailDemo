#!/bin/bash

IMAGE_NAME="mail-server"
CONTAINER_NAME="mail-demo"
DOCKER_CONTAINER=`docker ps -a | grep -o $IMAGE_NAME`
if [ "$DOCKER_CONTAINER" != $IMAGE_NAME ]; then
    docker build -t mail-server .
    docker create -it --name $CONTAINER_NAME  -p 18000:1025 -p 18001:1993 -p 18002:8000 -p 18003:1465 -p 18004:1143 -p 18005:1587 -p 18006:37809 mail-server
fi

docker start $CONTAINER_NAME
docker images
docker ps -a

# docker exec -it mail-demo /bin/bash