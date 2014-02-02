http://code.google.com/p/memcached/wiki/DevelopmentRepos
http://code.google.com/p/memcached/downloads/list

Install
http://libevent.org/
libevent – an event notification library 
sudo wget https://github.com/downloads/libevent/libevent/libevent-2.0.21-stable.tar.gz -P /opt
sudo tar zxvf /opt/libevent-2.0.21-stable.tar.gz -C /opt/
cd /opt/libevent-2.0.21-stable
./configure
sudo make && sudo make install

sudo wget http://memcached.googlecode.com/files/memcached-1.4.15.tar.gz -P /opt
sudo tar zxvf /opt/memcached-1.4.15.tar.gz -C /opt/
cd /opt/memcached-1.4.15
sudo ./configure 
sudo make && sudo make install

Test
memcached -h
LD_DEBUG=libs /usr/local/bin/memcached -v
sudo ln -s /usr/local/lib/libevent-2.0.so.5 /usr/lib64/libevent-2.0.so.5

Run
memcached -p 11211 -u memcached -m 64 -M -vv
sudo yum install telnet
telnet localhost 11211
----
memcached -d -m 512 -u root -l 192.168.0.190 -p 11211 -c 512 -P /usr/local/memcached/memcached.pid
启动参数详解
 -d：以守护进程方式启动。如果该参数没有指定，当按ctrl+c命令结束，memcache自动关闭
 -m：分配给memcache使用的最大内存数 单位是m，默认是64m
 -u: 指定运行memcache的用户
 -l: 指定监听的ip地址
 -p: 指定监听的tcp端口号，可以通过-u指定udp端口.默认是11211
 -c: 最大并发连接数
 -P: 报错进程id的文件

sudo /etc/init.d/iptables stop
(
sudo yum install telnet-server
sudo nano /etc/xinetd.d/telnet
	disable=no
sudo /etc/init.d/xinetd restart
)
----
Monitor
yum update httpd
sudo nano /etc/httpd/conf/httpd.conf
sudo nano /etc/sysconfig/iptables
sudo service iptables restart

sudo wget http://livebookmark.net/memcachephp/memcachephp.zip -P /var/www/html
sudo unzip /var/www/html/memcachephp.zip
sudo yum install php
http://192.168.0.190/memcache.php
memcache/password
sudo nano /var/www/html/memcache.php
	$MEMCACHE_SERVERS[] = 'http://192.168.0.190:11211'; 
