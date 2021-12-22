docker  run \
--name nacos -d \
-p 8848:8848 \
--privileged=true \
--restart=always \
-e JVM_XMS=256m \
-e JVM_XMX=256m \
-e MODE=standalone \
-e PREFER_HOST_MODE=hostname \
-v /data/nacos/logs:/home/nacos/logs \
-v /data/nacos/init.d/custom.properties:/home/nacos/init.d/custom.properties \
nacos/nacos-server