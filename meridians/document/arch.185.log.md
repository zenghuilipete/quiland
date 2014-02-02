#### rsyslog ####
    http://www.rsyslog.com/
    	http://www.rsyslog.com/files/download/rsyslog/rsyslog-7.2.6.tar.gz
#### loganalyzer ####
    http://loganalyzer.adiscon.com/downloads
    	http://download.adiscon.com/loganalyzer/loganalyzer-3.6.3.tar.gz

### 1.下载安装 ###
#### 1.1 LAMP ####
> httpd是apache2之后的称呼,用于代表http服务器,apache用于象征该组织.

	sudo yum install mysql mysql-devel mysql-server php php-mysql php-pdo php-common php-cli php-gd httpd -y

	Installed:
	  libjpeg-turbo.x86_64 0:1.2.1-1.el6   mysql-devel.x86_64 0:5.1.67-1.el6_3   php.x86_64 0:5.3.3-22.el6       php-cli.x86_64 0:5.3.3-22.el6   php-common.x86_64 0:5.3.3-22.el6
	  php-gd.x86_64 0:5.3.3-22.el6         php-mysql.x86_64 0:5.3.3-22.el6       php-pdo.x86_64 0:5.3.3-22.el6
	Dependency Installed:
	  keyutils-libs-devel.x86_64 0:1.4-4.el6    krb5-devel.x86_64 0:1.10.3-10.el6_4.1      libcom_err-devel.x86_64 0:1.41.12-14.el6    libselinux-devel.x86_64 0:2.0.94-5.3.el6
	  libsepol-devel.x86_64 0:2.0.41-4.el6      openssl-devel.x86_64 0:1.0.0-27.el6_4.2    zlib-devel.x86_64 0:1.2.3-29.el6
	Updated:
	  httpd.x86_64 0:2.2.15-26.el6.centos
	Dependency Updated:
	  e2fsprogs.x86_64 0:1.41.12-14.el6              e2fsprogs-libs.x86_64 0:1.41.12-14.el6    httpd-tools.x86_64 0:2.2.15-26.el6.centos    krb5-libs.x86_64 0:1.10.3-10.el6_4.1
	  krb5-workstation.x86_64 0:1.10.3-10.el6_4.1    libcom_err.x86_64 0:1.41.12-14.el6        libss.x86_64 0:1.41.12-14.el6                openssl.x86_64 0:1.0.0-27.el6_4.2
	  zlib.x86_64 0:1.2.3-29.el6
	Replaced:
	  libjpeg.x86_64 0:6b-46.el6

#### 1.2 依赖包 ####
libestr

    sudo axel -n 10 -a -S5 http://libestr.adiscon.com/files/download/libestr-0.1.5.tar.gz -o /opt
    sudo tar zxvf /opt/libestr-0.1.5.tar.gz -C /opt/
    cd /opt/libestr-0.1.5
    32bit:
    	sudo ./configure --libdir=/usr/lib --includedir=/usr/include/
    64bit:
    	sudo ./configure CC="gcc -m64" --prefix=/usr --libdir=/usr/lib64
    sudo make && sudo make install

libee

	sudo axel -n 10 -a -S5 http://www.libee.org/download/files/download/libee-0.4.1.tar.gz -o /opt
	sudo tar zxvf /opt/libee-0.4.1.tar.gz -C /opt/
	cd /opt/libee-0.4.1
	32bit:
		sudo ./configure --libdir=/usr/lib --includedir=/usr/include/
	64bit:
		sudo ./configure --prefix=/usr CC="gcc -m64" PKG_CONFIG_PATH="/usr/lib64/pkgconfig" --libdir=/usr/lib64
	sudo make && sudo make install

json-c

	https://github.com/json-c/json-c/downloads
	sudo wget https://github.com/downloads/json-c/json-c/json-c-0.10-nodoc.tar.gz -P /opt
	sudo tar zxvf /opt/json-c-0.10-nodoc.tar.gz -C /opt/
	cd /opt/json-c-0.10
	sudo ./configure CC="gcc -m64" --prefix=/usr --libdir=/usr/lib64
	sudo make && sudo make install
	sudo cp json_object_iterator.h /usr/local/include/json/

#### 1.3 rsyslog ####
	sudo axel -n 10 -a -S5 http://www.rsyslog.com/files/download/rsyslog/rsyslog-7.2.6.tar.gz -o /opt
	sudo tar zxvf /opt/rsyslog-7.2.6.tar.gz -C /opt/
	cd /opt/rsyslog-7.2.6
	sudo ./configure --enable-mysql --prefix=/usr/local/rsyslog
		#如果 No package 'libestr' found,指定PKG_CONFIG_PATH 和LIBESTR_LIBS路径:
		sudo ./configure CC="gcc -m64" --enable-mysql --prefix=/usr/local/rsyslog CC="gcc -m64" PKG_CONFIG_PATH="/usr/lib64/pkgconfig" LIBESTR_LIBS=/usr/lib64/libestr.so

		#如果 No package 'libuuid' found,直接yum吧:
		yum install libuuid-devel
	sudo make && sudo make install

#### 1.4 rsyslog-mysql ####
	rpm -qa rsyslog-mysql
	sudo yum install rsyslog-mysql
	Installed:
	  rsyslog-mysql.x86_64 0:5.8.10-6.el6
	Dependency Updated:
	  rsyslog.x86_64 0:5.8.10-6.el6
	
	(sudo apt-get install -y rsyslog-mysql)

### 2.配置文件 ###
    sudo cp /etc/rsyslog.conf /etc/rsyslog.conf.origin
    sudo nano /etc/rsyslog.conf

**关键字**

1. user passwd 
1. 514 
1. log2

[arch.185.log.rsyslog.conf.txt]

### 3.启动服务 ###
    sudo chkconfig --add rsyslog
    sudo chkconfig --add mysqld
    sudo chkconfig --add httpd
    sudo chkconfig rsyslog on
    sudo chkconfig httpd on
    sudo chkconfig mysqld on
    sudo service rsyslog start
    sudo service httpd start
    sudo chkconfig syslog off
    sudo service syslog stop
    sudo service mysqld start

### 4.配置DATABASE ###
	cat /opt/rsyslog-7.2.6/plugins/ommysql/createDB.sql
	(cat /usr/share/doc/rsyslog-mysql-5.8.10/createDB.sql)

	/usr/bin/mysqladmin -u root password 'root'
	mysql -uroot -proot < /opt/rsyslog-7.2.6/plugins/ommysql/createDB.sql
	
	mysql -uroot -proot
	mysql> GRANT ALL privileges ON Syslog.* TO user@localhost IDENTIFIED BY 'passwd';
	mysql> flush privileges;
	mysql> exit

#### [rsyslog conf templates](http://www.rsyslog.com/doc/rsyslog_conf_templates.html) ####

### 5.测试运行 ###
    sudo /etc/init.d/rsyslog restart
    ps -ef | grep -v grep | grep rsyslog 

    mysql -uuser -ppasswd
    use Syslog;
    mysql> select count(*) from SystemEvents;
    有数据说明成功

    日志的日志查错:
    sudo tail /var/log/messages

    rsyslogd-2066: could not load module '/lib64/rsyslog/ommysql.so', dlopen: /lib64/rsyslog/ommysql.so
    需要安装:sudo yum install rsyslog-mysql

### 6.1.安装LogAnalyzer ###
    sudo axel -n 10 -a -S5 http://download.adiscon.com/loganalyzer/loganalyzer-3.6.3.tar.gz -o /opt
    sudo tar zxvf /opt/loganalyzer-3.6.3.tar.gz -C /opt/
    sudo cp -r /opt/loganalyzer-3.6.3/src /var/www/html/loganalyzer
    sudo cp -r /opt/loganalyzer-3.6.3/contrib/* /var/www/html/loganalyzer
    sudo chown apache.apache -R /var/www/html/loganalyzer
    
    cd /var/www/html/loganalyzer
    sudo chmod u+x configure.sh secure.sh
    sudo ./configure.sh
    
    sudo /etc/init.d/httpd restart
    sudo cat /var/log/httpd/error_log

### 6.2.配置LogAnalyzer ###
    http://192.168.0.185/loganalyzer/install.php?step=3
    	Syslog
    	logcon_
    	user/passwd
    http://syslog185.com/loganalyzer/install.php?step=6
    	user/passwd/passwd
    http://syslog185.com/loganalyzer/install.php?step=7
    	MYSQL Native
    	Syslog
    	SystemEvents
    	Enabled Row Counting

###7.在日志服务器查询 ###
	ll /var/log/ | grep log4j
	-rw-------. 1 root  root  109256 Mar 23 05:53 log4j_debug.log
	-rw-------. 1 root  root   0 Mar 22 07:29 log4j_error.log
	-rw-------. 1 root  root   12972 Mar 23 05:53 log4j_info.log
	-rw-------. 1 root  root   0 Mar 22 07:23 log4j.log

    SELECT
    	se.Facility,
    	se.Priority,
    	se.Message
    FROM
    	SystemEvents se
    WHERE
    	se.FromHost != 'meridians-rsyslog'
    ORDER BY se.EventID DESC

    SELECT ID,ReceivedAt,Facility,Priority,FromHost,SysLogTag,Message 
	from SystemEvents
	order by ID desc limit 100;

    cat /var/www/html/loganalyzer/config.php | grep Tag
    $CFG['Charts'][] = array ( "DisplayName" => "SyslogTags", "chart_type" => CHART_CAKE, "chart_width" => 400, "chart_field" => SYSLOG_SYSLOGTAG, "maxrecords" => 10, "showpercent" => 0, "chart_enabled" => 1 );

### 8.APP服务器Log4j配置 ###
    <?xml version="1.0" encoding="utf-8" ?>  
    <log4j:configuration xmlns:log4j="http://jakarta.apache.org/log4j/">
    	<appender name="STDOUT" class="org.apache.log4j.ConsoleAppender">
    		<layout class="org.apache.log4j.PatternLayout">
    			<param name="ConversionPattern" value="%d %-5p [%c] %t - %m%n" />
    		</layout>
    
    		<filter class="org.apache.log4j.varia.LevelRangeFilter">
    			<param name="levelMin" value="debug" />
    			<param name="levelMax" value="error" />
    			<param name="AcceptOnMatch" value="true" />
    		</filter>
    	</appender>
    
    	<appender name="SYSLOG" class="org.apache.log4j.net.SyslogAppender">
    		<param name="SyslogHost" value="192.168.0.185" />
    		<param name="Facility" value="local2" />
    		<param name="Header" value="true" />
    		<layout class="org.apache.log4j.PatternLayout">
    			<param name="ConversionPattern" value="%t %5r %-5p %-21d{yyyyMMdd[HH]mm:ss,SSS} %c{2} [%x] %m%n" />
    		</layout>
    	</appender>
    
    	<root>
    		<priority value="debug" />
    		<appender-ref ref="SYSLOG" />
    		<appender-ref ref="STDOUT" />
    	</root>
    </log4j:configuration>

使用startswith比contains和regex系统开销要小，但是测试了几次都没有正确送过来数据。查了半天才知道，为了符合什么ITC规范，在前面加了一个空格。要这样书写才可以： $msg startswith ' jimmy'   [注意jimmy前面要加一个空格！]，实在是无语。