#!/bin/sh

PID=`ps -ef|grep 'com.caucho.boot.WatchdogManager'|grep 'resin_test'|grep -v perl|grep -v grep|awk '{print $2}'`
if [ -n "${PID}" ]; then
    kill -9 ${PID}
fi
sleep 2

su -c "/home/server/resin_test/bin/resin.sh start" resin

