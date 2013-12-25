#####下载#####
- **[mysql](http://dev.mysql.com/downloads/mysql/ "mysql download")**
- **[workbench](http://dev.mysql.com/downloads/tools/workbench/ "workbench download")**
- [mysql-5.6.15-debian6.0-x86_64.deb](http://cdn.mysql.com/Downloads/MySQL-5.6/mysql-5.6.15-debian6.0-x86_64.deb)

[http://dev.mysql.com/doc/refman/5.6/en/linux-installation-debian.html](http://dev.mysql.com/doc/refman/5.6/en/linux-installation-debian.html)

#####监控旧版本#####
	sudo netstat -tap | grep mysql

	whereis mysql
	mysql: /usr/bin/mysql /etc/mysql /usr/lib/mysql /usr/bin/X11/mysql /usr/share/mysql /usr/share/man/man1/mysql.1.gz
	
	ls /etc/mysql
	conf.d  debian.cnf  debian-start  my.cnf
	
	ls /usr/lib/mysql
	plugin
	
	ls /usr/share/mysql
	charsets           debian-start.inc.sh  fill_help_tables.sql  japanese                      ndb-config-2-node.ini  russian
	config.huge.ini    dutch                french                korean                        norwegian              serbian
	config.medium.ini  echo_stderr          german                mysqld_multi.server           norwegian-ny           slovak
	config.small.ini   english              greek                 mysql_system_tables_data.sql  polish                 spanish
	czech              errmsg-utf8.txt      hungarian             mysql_system_tables.sql       portuguese             swedish
	danish             estonian             italian               mysql_test_data_timezone.sql  romanian               ukrainian

#####卸载旧版本#####
	sudo apt-get remove --purge mysql-server mysql-client mysql-common
	sudo apt-get autoremove
	sudo apt-get autoclean

#####安装#####
	sudo apt-get install libaio-dev
	sudo dpkg -i mysql-5.6.15-debian6.0-x86_64.deb

#####配置#####
[http://dev.mysql.com/doc/refman/5.6/en/binary-installation.html](http://dev.mysql.com/doc/refman/5.6/en/binary-installation.html)

	sudo groupadd mysql
	sudo useradd -r -g mysql mysql
	cd /opt/mysql/server-5.6/
	sudo mkdir data
	sudo chown mysql:mysql data
	sudo mkdir log
	sudo chown mysql:mysql log

	sudo cp /opt/mysql/server-5.6/share/english/errmsg.sys /usr/share/mysql/errmsg.sys
	sudo chown mysql:mysql /usr/share/mysql/errmsg.sys

	sudo scripts/mysql_install_db --user=mysql --no-defaults
	sudo cp support-files/my-default.cnf /etc/mysql/my.cnf
	sudo cp support-files/mysql.server /etc/init.d/mysql.server
	sudo cp support-files/mysql-log-rotate /etc/logrotate.d/mysql.server

cat /etc/mysql/my.cnf

	[client]
	port            = 3306
	socket          = /tmp/mysql56.sock
	
	[mysqld_safe]
	port            = 3306
	socket          = /tmp/mysql56.sock
	nice            = 0
	
	[mysqld]
	user            = mysql
	pid-file        = /tmp/mysql56.pid
	port            = 3306
	socket          = /tmp/mysql56.sock
	
	basedir         = /opt/mysql/server-5.6
	datadir         = /opt/mysql/server-5.6/data
	tmpdir          = /tmp
	lc-messages-dir = /usr/share/mysql
	#bind-address           = 127.0.0.1
	
	key_buffer              = 16M
	max_allowed_packet      = 16M
	thread_stack            = 192K
	thread_cache_size       = 8
	myisam-recover         = BACKUP
	query_cache_limit       = 1M
	query_cache_size        = 16M
	log_error= /opt/mysql/server-5.6/log/error.log
	expire_logs_days        = 10
	max_binlog_size         = 100M
	
	[mysqldump]
	quick
	quote-names
	max_allowed_packet      = 16M
	
	[mysql]
	#no-auto-rehash # faster start of mysql but no tab completition
	
	[isamchk]
	key_buffer              = 16M
	
	sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES
	!includedir /etc/mysql/conf.d/
	erichan@erichan-OptiPlex-790:~$	

#####启动#####
	sudo service mysql.server start

	mysqladmin -u root password 'root'

####Benchmarking####
#####What to Measure#####

- Throughput

	TPC，事務處理性能委員會，其成員由 Acer，Bull，Compaq，EMC，HP，IBM, MS, NCR, SCO, Siemens, Sun, Unisys 構成。目前擁有的測試基準包括：TPC-C （線上交易處理 OLTP）；TPC-H （決策支援系統 DSS）；TPC-W（電子商務e-Commerce）；TPC-R（決策支援系統 DSS（商務報表））；我們所討論的 TPC-C，其測試模型定義為模擬一個訂貨系統的處理過程，輸出為測試系統每分鐘能夠處理的新訂單數。（同時還要處理另外四種交易：支付、訂單狀態、配送、庫存狀態)
- Response time or latency
- Concurrency
- Scalability
	
#####Full-Stack Tools#####
- **[ab](http://httpd.apache.org/docs/2.0/programs/ab.html)**	
- **[http_load](http://www.acme.com/software/http_load/)**	
- **[JMeter](http://jakarta.apache.org/jmeter/)**	

#####Single-Component Tools#####
- **[mysqlslap](http://dev.mysql.com/doc/refman/5.1/en/mysqlslap.html)**
- **[MySQL Benchmark Suite(sql-bench)](http://dev.mysql.com/doc/en/mysql-benchmarks.html/)**
- **[Super Smack](http://vegan.net/tony/supersmack/)**
- **[Database Test Suite](http://sourceforge.net/projects/osdldbt/)** dbt2
- **[Percona’s TPCC-MySQL Tool](https://launchpad.net/perconatools)**
- **[sysbench](https://launchpad.net/sysbench)**

