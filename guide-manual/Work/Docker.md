
***
目录  
[安装](#安装)  
[安装Elasticsearch](#安装Elasticsearch)

***

# 安装
参考：https://www.cnblogs.com/caoweixiong/p/12186736.html

uname -r :查看内核版本  
准备：  
yum -y install gcc  
yum -y install gcc-c++  
删除旧版本  
yum remove docker \ docker-client \ docker-client-latest \ docker-common \ docker-latest \ docker-latest-logrotate \ docker-logrotate \ docker-selinux \ docker-engine-selinux \ docker-engine  
安装依赖包：  
yum install -y yum-utils device-mapper-persistent-data lvm2  
设置稳定镜像：  
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo  
更新yum软件包索引：  
yum makecache fast  

列出docker 版本：
yum list docker-ce.x86_64  --showduplicates | sort -r

## Centos7 安装  
yum install docker-ce-19.03.9 docker-ce-cli-19.03.9 containerd.io

## Centos8 安装  
yum install -y https://mirrors.aliyun.com/docker-ce/linux/centos/7/x86_64/edge/Packages/containerd.io-1.2.13-3.2.el7.x86_64.rpm

安装docker ce  
yum install docker-ce docker-ce-cli containerd.io

## 配置

启动  
systemctl start docker

修改中国镜像：  
改用中国的 docker 镜像仓库  
阿里云镜像  
修改/etc/docker/daemon.json文件添加：  
```
{
  "registry-mirrors": ["https://6kx4zyno.mirror.aliyuncs.com"]
}
```
https://hub-mirror.c.163.com  
重启  
systemctl daemon-reload     #重启加速配置文件  
systemctl restart docker    #重启docker后台服务

修改DNS  
vi /etc/resolv.conf  
nameserver 8.8.8.8  
nameserver 8.8.8.4

***
# Docker 使用
参考：https://docs.docker.com/engine/reference/commandline/  
## 基本使用
1. 查看版本  docker version  
1. 查看docker日志  cat /var/log/docker  
1. 搜索镜像  docker search tomcat  
1. 查看当前所有镜像  docker images  
1. 下载镜像  docker pull centos  
1. 运行容器  docker run centos echo “hello world”  
1. 启动容器 docker start {containerId/containerName}
1. 进入容器 docker exec -it {containerId/containerName} /bin/bash


## docker搭建Java开发环境  
centos7.2 + JDK1.8  
通过docker search 软件名 命令获取centos  
docker search centos //此时可以看到可用的centos镜像列表  
获取centos镜像  
docker pull centos:7.2  
通过docker images 可以查看本地镜像  
docker images  
//启动容器命令 docker run options containsID argument  
- options参数说明  
  - -i：表示以“交互模式”运行容器  
  - -t：表示容器启动后会进入其命令行  
  - -v：表示需要将本地哪个目录挂载到容器中，格式：-v <宿主机目录>:<容器目录>  
  - --name: 指定容器的名称，如果未指定容器名称则会随机生成一个名称  
docker run --name Glearn -itv /home/root/softwares/:/mnt/software/ 0d120b6ccaa8 /bin/bash  
### 启动容器  
docker start Glearn  
### 登录容器  
docker attach Glearn  

### 安装wget库  
sudo yum install wget  
#进入usr/local/src目录下  
cd /usr/local/src  
#获取JDK1.8包  
wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u161-b12/2f38c3b165be4555a1fa6e98c45e0808/jdk-8u161-linux-x64.tar.gz"  
#解压jdk-8u161-linux-x64.tar.gz  
tar -zxvf jdk-8u161-linux-x64.tar.gz  
#重命名为JDK8  
mv jdk1.8.0_161 jdk8  
#配置环境变量  
sudo vim /etc/profile #打开环境变量配置文件  
#增加下面内容到该文件最后  
export JAVA_HOME=/usr/local/jdk8  
export PATH=$JAVA_HOME/bin:$PATH  
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar  
#使环境生效  
source /etc/profile  

#删除镜像
docker rmi -f {image id}

docker run -d -P --name dev env-dev:1.0.0 /bin/bash

docker run -p 6379:6379 --name dev -itv /home/guanyw/softwares/:/mnt/software/ 73b6e6469d75 /bin/bash

## Docker 搭建 mysql 服务
docker pull mysql:5.7   # 拉取 mysql 5.7  
docker pull mysql       # 拉取最新版mysql镜像  
sudo docker run -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7  
–name：容器名，此处命名为mysql  
-e：配置信息，此处配置mysql的root用户的登陆密码  
-p：端口映射，此处映射 主机3306端口 到 容器的3306端口  
查看容器是否运行  
docker container ls  
docker本地连接mysql客户端  
docker exec -it mysql bash  
mysql -uroot -proot  

### 数据和配置外置
1. 进入容器
docker exec -it mysql5.7 /bin/bash
2. 查看配置
cat /etc/mysql/my.cnf
3. 在容器外创建同名文件
4. 停止mysql 并删除容器
docker stop mysql5.7 && docker rm mysql5.7
5. 在本地创建挂载目录
mkdir -p /opt/mysql/data
6. 重新创建容器
docker run --name mysql5.7 --restart always --privileged=true -p 4306:3306 -v /opt/mysql/config/mysqld.cnf:/etc/mysql/mysql.conf.d/mysqld.cnf -v /opt/mysql/data:/var/lib/mysql -e MYSQL_USER="fengwei" -e MYSQL_PASSWORD="pwd123" -e MYSQL_ROOT_PASSWORD="rootpwd123" -d mysql:5.7

## Docker 搭建 redis 服务
docker pull redis  
sudo docker run -p 6379:6379 --name redis -d redis

## Docker 生成镜像
构建Docker镜像，用于运行，Docker构建镜像的两种方法：  
1.使用docker commit 命令；  
2.使用docker build命令和Dockerfile文件；  

docker ps -a  
docker commit {Container Id}  {name:version}  
#查看容器列表  
docker images   
#运行  
docker run -d -p 28080:8080 --name {name} -itv /home/root/softwares/:/mnt/software/ {containerId} /bin/bash  

## 安装Elasticsearch
- docker pull elasticsearch:5.5.0
- docker run -d -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -e ES_JAVA_OPTS="-Xms512m -Xmx512m" --name es5 elasticsearch:5.5.0  
9300：集群节点指点的tcp通讯端口  
9200：http协议的web客户端RESTful端口  
- 安装中文分词器
./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.10.1/elasticsearch-analysis-ik-7.10.1.zip

查找指定image后创建的镜像和依赖关系
docker image inspect --format='{{.RepoTags}} {{.Id}} {{.Parent}}' $(docker image ls -q --filter since=image-name)
删除指定image
docker images | grep image | awk '{print $3}' | xargs docker rmi
删除指定容器
docker rm -f $(docker ps -a | grep "imsage-name" | awk '{print $1}')
