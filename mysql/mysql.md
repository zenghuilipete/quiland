#####下载#####
- **[mysql](http://dev.mysql.com/downloads/mysql/ "mysql download")**
- **[workbench](http://dev.mysql.com/downloads/tools/workbench/ "workbench download")**
- [mysql-5.6.15-debian6.0-x86_64.deb](http://cdn.mysql.com/Downloads/MySQL-5.6/mysql-5.6.15-debian6.0-x86_64.deb)

[http://dev.mysql.com/doc/refman/5.6/en/linux-installation-debian.html](http://dev.mysql.com/doc/refman/5.6/en/linux-installation-debian.html)

#####监控旧版本#####
	ps -ef |grep mysqld
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

sudo nano /etc/mysql/my.cnf

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

#####启动#####
	sudo service mysql.server start

	mysqladmin -u root password 'root'

####机制####
#####配置加载#####
启动时，查找配置文件顺序，相同配置优先级：后面的配置覆盖前面的

	erichan@erichan-OptiPlex-790:/opt/mysql/server-5.6/bin$ ./mysql --help | grep my.cnf
	                      order of preference, my.cnf, $MYSQL_TCP_PORT,
	/etc/my.cnf /etc/mysql/my.cnf /opt/mysql/server-5.6/etc/my.cnf ~/.my.cnf 

#####单进程多线程#####

- 查看线程
	- IO线程
	- 主线程
	- 锁监控线程
	- 错误监控线程
	 
	`mysql> show engine innodb status \G;`

- 查看引擎版本

	`mysql> show variables like 'innodb_version' \G;`

> 	Variable_name: innodb_version
> 	Value: 5.6.15

- 内存
	- 缓冲池
	- 重做日志缓冲池
	- 额外内存池
 
	`mysql> show engine innodb status \G;`
	> BUFFER POOL AND MEMORY
> 	Total memory allocated 137363456; in additional pool allocated 0
> 	Dictionary memory allocated 43128
> 	Buffer pool size   8191
> 	Free buffers       8043
> 	Database pages     148
> 	Old database pages 0
> 	Modified db pages  0
> 	Pending reads 0
> 	Pending writes: LRU 0, flush list 0, single page 0
> 	Pages made young 0, not young 0
> 	0.00 youngs/s, 0.00 non-youngs/s
> 	Pages read 148, created 0, written 1
> 	0.00 reads/s, 0.00 creates/s, 0.00 writes/s
> 	No buffer pool page gets since the last printout
> 	Pages read ahead 0.00/s, evicted without access 0.00/s, Random read ahead 0.00/s
> 	LRU len: 148, unzip_LRU len: 0
> 	I/O sum[0]:cur[0], unzip sum[0]:cur[0]

	- Buffer pool size 缓冲池共多少缓存帧 每帧16K
	- Free buffers 缓冲池空闲帧
	- Database pages 缓冲池已用帧
	- Modified db pages 脏页数量

- 文件
	- 表结构 frm
	- InnoDB表 ibd
	- InnoDB表空间 ibdata1...
	- 重做日志 ib\_logfile0 ib\_logfile1


- 表空间
	- 段 数据段：B+树页节点 索引段：B+树非页节点 回滚段
	- 区：64个连续页组成 1M
	- 页：即块 16K

**支持水平分区 行分 不支持垂直分区 列分**

- 索引
	- B+树：高扇出的平衡查找树
	- 聚集索引：数据按照主键顺序存放 非物理连续 数据页通过双向链表链接
	- 非聚集索引/辅助索引：
	- 自适应哈希索引



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

