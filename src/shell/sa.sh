#!/bin/sh
echo "================== stop resin =================="

PID=`ps -ef|grep 'com.caucho.boot.WatchdogManager'|grep 'resin_test'|grep -v perl|grep -v grep|awk '{print $2}'`
if [ -n "${PID}" ]; then
    kill -9 ${PID}
fi
sleep 2

echo "================== git pull webapp =================="
cd /home/workspace/visa/
git pull
chmod +x src/shell/*

echo "================== mvn =================="
mvn 

chown -R resin:resin /home/workspace/visa/target/visa

echo "================== restart resin =================="
su -c "/home/server/resin_test/bin/resin.sh start" resin
