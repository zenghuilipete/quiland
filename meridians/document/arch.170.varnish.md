https://www.varnish-cache.org/
https://www.varnish-cache.org/releases/varnish-cache-3.0.3

安装
    curl http://repo.varnish-cache.org/debian/GPG-key.txt | sudo apt-key add -
    echo "deb http://repo.varnish-cache.org/ubuntu/ precise varnish-3.0" | sudo tee -a /etc/apt/sources.list
    sudo apt-get update
    sudo apt-get install varnish

配置
sudo nano /etc/varnish/default.vcl
[arch.170.vcl.txt]

命令行
sudo varnishd -f /etc/varnish/default.vcl -s malloc,1G -T 127.0.0.1:2000 -a 192.168.0.170:8888
	-a <[hostname]:port> listen address
	-f <filename> VCL
	-p <parameter=value> set tunable parameters
	-S <secretfile> authentication secret for management
	-T <hostname:port> Management interface
	-s <storagetype,options> where and how to store objects

ls /usr/bin/ | grep varnish
varnishadm
varnishhist
varnishlog
varnishncsa
varnishreplay
varnishsizes
varnishstat
varnishtest
varnishtop

sudo  service varnish start
sudo pkill varnishd

测试
curl http://192.168.0.170:8888

日志
sudo /usr/bin/varnishlog