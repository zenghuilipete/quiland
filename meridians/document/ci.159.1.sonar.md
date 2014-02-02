JDK
sudo mkdir /opt/jdk
sudo scp eric@192.168.0.150:/opt/jdk/jdk-6u38.bin /opt/jdk/jdk-6u38.bin

MYSQL
sudo apt-get update
sudo apt-get install mysql-server
	root/root

1.下载
sudo wget http://dist.sonar.codehaus.org/sonar-3.4.1.zip -P /opt/
或者
sudo apt-get install axel
sudo axel -n 16 -o /opt/ http://dist.sonar.codehaus.org/sonar-3.4.1.zip

2.安装
sudo apt-get install zip
sudo unzip /opt/sonar-3.4.1.zip -d /opt/

3.配置
3.1.sudo chown -R eric:eric /opt/sonar-3.4.1
3.2.sudo nano /opt/sonar-3.4.1/conf/sonar.properties
	#----- MySQL 5.x/6.x
	sonar.jdbc.url: jdbc:mysql://localhost:3306/sonar?useUnicode=true&characterEncoding=utf8
	sonar.jdbc.driverClassName: com.mysql.jdbc.Driver
	sonar.jdbc.validationQuery: select 1
3.3.mysql -u root -p < /opt/sonar-3.4.1/extras/database/mysql/create_database.sql
3.4.查看sonar数据库
mysqlshow sonar -u sonar -p

4.启动
/opt/sonar-3.4.1/bin/linux-x86-64/sonar.sh console
4.1查看sonar数据库记录
mysqlshow sonar -u sonar -p
4.2查看进程
ps aux | grep sonar
4.3访问
http://192.168.0.159:9000
default user/password: admin/admin

(5.服务
sudo nano /etc/init.d/sonar
	#! /bin/sh
	/usr/bin/sonar $*
sudo ln -s /opt/sonar-3.4.1/bin/linux-x86-64/sonar.sh /usr/bin/sonar
sudo chmod 755 /etc/init.d/sonar
sudo update-rc.d sonar defaults
	update-rc.d: warning: /etc/init.d/sonar missing LSB information
	update-rc.d: see <http://wiki.debian.org/LSBInitScripts>
	 Adding system startup for /etc/init.d/sonar ...
	   /etc/rc0.d/K20sonar -> ../init.d/sonar
	   /etc/rc1.d/K20sonar -> ../init.d/sonar
	   /etc/rc6.d/K20sonar -> ../init.d/sonar
	   /etc/rc2.d/S20sonar -> ../init.d/sonar
	   /etc/rc3.d/S20sonar -> ../init.d/sonar
	   /etc/rc4.d/S20sonar -> ../init.d/sonar
	   /etc/rc5.d/S20sonar -> ../init.d/sonar
	eric@meridians-sonar:~$
service sonar start
sudo update-rc.d -f sonar remove
)

6.支持(Jenkins)远程访问
#----- Embedded database H2
# Note : it does not accept connections from remote hosts, so the
# sonar server and the maven plugin must be executed on the same host.
(
To check PRIVILEGES with:
select Host,User,Create_priv,Grant_priv from mysql.user;

mysql -uroot -proot
GRANT ALL ON sonar.* TO 'sonar'@'%' IDENTIFIED BY 'sonar';
GRANT ALL ON sonar.* TO 'sonar'@'localhost' IDENTIFIED BY 'sonar';
FLUSH PRIVILEGES;
quit;
)

sudo nano /etc/mysql/my.cnf
	#bind-address            = 127.0.0.1
sudo service mysql restart