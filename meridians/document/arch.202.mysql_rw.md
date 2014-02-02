读写分离(Read/Write Splitting)

环境
OS:linuxMint14
hostname:meridians-mysql0
IP:10.11.58.222

安装
apt-get::
  sudo apt-get install mysql-proxy
bin::
  sudo wget http://cdn.mysql.com/Downloads/MySQL-Proxy/mysql-proxy-0.8.3-linux-glibc2.3-x86-64bit.tar.gz -P /opt
  sudo tar xvzf /opt/mysql-proxy-0.8.3-linux-glibc2.3-x86-64bit.tar.gz -C /opt
  sudo mv /opt/mysql-proxy-0.8.3-linux-glibc2.3-x86-64bit /opt/mysql_proxy
src::
  sudo wget https://launchpadlibrarian.net/86414129/mysql-proxy-0.8.2.tar.gz -P /opt
  sudo tar xvzf /opt/mysql-proxy-0.8.2.tar.gz -C /opt

测试
mysql-proxy -V
  mysql-proxy 0.8.1
    chassis: mysql-proxy 0.8.1
    glib2: 2.30.1
    libevent: 2.0.19-stable
    LUA: Lua 5.1.4
      package.path: /usr/lib/mysql-proxy/lua/?.lua
      package.cpath: /usr/lib/mysql-proxy/lua/?.so
  -- modules
    admin: 0.8.1
    proxy: 0.8.1

配置
sudo nano /usr/share/mysql-proxy/rw-splitting.lua
  -- connection pool
  if not proxy.global.config.rwsplit then
          proxy.global.config.rwsplit = {
                  min_idle_connections = 1,
                  max_idle_connections = 2,
                  is_debug = true
          }
  end
sudo nano /usr/share/mysql-proxy/admin-sql.lua

读写分离测试
::proxy::
mysql -uroot -proot
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' identified by 'root'; 
FLUSH PRIVILEGES;

::master10.11.58.238::
mysql grant all privileges on *.* to root@10.11.58.222 identified by 'root';
mysql FLUSH PRIVILEGES;

启动1
sudo mysql-proxy \
 --admin-username=root \
 --admin-password=root \
 --proxy-backend-addresses=10.11.58.238:3306 \
 --proxy-read-only-backend-addresses=10.11.58.243:3306 \
 --proxy-lua-script=/usr/share/mysql-proxy/rw-splitting.lua \
 --admin-lua-script =/usr/share/mysql-proxy/admin-sql.lua \
 --daemon \
 --log-file=/home/eric/mysql-proxy.log \
 --log-level=debug \
 --pid-file=/home/eric/mysql-proxy.pid

启动2
mysql-proxy.cnf
	[mysql-proxy]
	daemon = true
	admin-username=root
	admin-password=root
	proxy-backend-addresses=10.11.58.238:3306
	proxy-read-only-backend-addresses=10.11.58.243:3306
	proxy-lua-script=/usr/share/mysql-proxy/rw-splitting.lua
	admin-lua-script =/usr/share/mysql-proxy/admin-sql.lua
	log-file=/home/eric/mysql-proxy.log
	log-level=debug
	pid-file=/home/eric/mysql-proxy.pid

chmod 660 /home/eric/mysql-proxy.cnf 
mysql-proxy --defaults-file=/home/eric/mysql-proxy.cnf

sudo pkill mysql-proxy
ps -ef | grep mysql-proxy | grep -v grep

CLIENT1
mysql -uroot -proot -P4040
CLIENT2
mysql -uroot -proot -P4040
INSERT INTO test1 VALUES ('4', 'Idled');
----
1 程序修改mysql操作类
可以参考PHP实现的Mysql读写分离，阿权开始的本项目，以php程序解决此需求。
优点：直接和数据库通信，简单快捷的读写分离和随机的方式实现的负载均衡，权限独立分配
缺点：自己维护更新，增减服务器在代码处理

2 amoeba
http://amoeba.meidusa.com/
http://docs.hexnova.com/amoeba/
http://sourceforge.net/projects/amoeba/files/
优点：直接实现读写分离和负载均衡，不用修改代码，有很灵活的数据解决方案
缺点：自己分配账户，和后端数据库权限管理独立，权限处理不够灵活

http://docs.hexnova.com/amoeba/limitations.html
目前还不支持事务
暂时不支持存储过程（近期会支持）
不适合从amoeba导数据的场景或者对大数据量查询的query并不合适（比如一次请求返回10w以上甚至更多数据的场合）
暂时不支持分库分表，amoeba目前只做到分数据库实例，每个被切分的节点需要保持库表结构一致

`amoeba.xml：主配置文件，配置所有数据源以及Amoeba 自身的参数设置；
`rule.xml：配置所有Query 路由规则的信息；
`functionMap.xml：配置用于解析Query 中的函数所对应的Java 实现类；
`rullFunctionMap.xml：配置路由规则中需要使用到的特定函数的实现类；

3 mysql-proxy
http://dev.mysql.com/downloads/mysql-proxy/
http://dev.mysql.com/doc/refman/5.6/en/mysql-proxy.html
http://jan.kneschke.de/2007/8/1/mysql-proxy-learns-r-w-splitting/
优点：直接实现读写分离和负载均衡，不用修改代码，master和slave用一样的帐号
缺点：字符集问题，lua语言编程，还只是alpha版本，时间消耗有点高


如果你不能安装软件来解决读写分离，那可以尝试阿权的项目解决思路。
如果你可以安装软件，那amoeba是不错的，mysql-proxy不太建议，目前只有alpha版本，效率还不太理想，amoeba目前在阿里巴巴是内部项目，正在生产环境使用的。
