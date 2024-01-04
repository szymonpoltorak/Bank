#!/bin/sh

sh gen.sh

docker compose -f ../docker-compose.yml up --build -d
