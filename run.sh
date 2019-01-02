#!/bin/bash
# @description 启动java微服务
# @Author: sundanwei
# @Date:   2018-11-26 17:44:45
# @Last Modified by:   sundanwei
# @Last Modified time: 2018-11-15 17:44:45

WORKDIR=$(cd $(dirname $0); pwd)
cd $WORKDIR
# 微服务jar包名称,不同的微服务修改此jar包名称即可
MICRO_JARNAME=driverapiservermicrosrv.jar

case $1 in
        start)
                nohup java -jar $MICRO_JARNAME 2>&1 >> info.log 2>&1 /dev/null &
                echo "服务已启动..."
                sleep 1
        ;;
        stop)
                kill `ps -X| grep $MICRO_JARNAME |grep -v grep|awk '{print $1}'`
                echo "服务已停止..."
                sleep 1
        ;;
        restart)
                kill `ps -X|grep $MICRO_JARNAME|grep -v grep|awk '{print $1}'`
                sleep 1
                nohup java -jar $MICRO_JARNAME 2>&1 >> info.log 2>&1 /dev/null &
                echo "服务已重启..."
                sleep 1
        ;;
        *)
                echo "$0 {start|stop|restart}"
                exit 4
        ;;
esac