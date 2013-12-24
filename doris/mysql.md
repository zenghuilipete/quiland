#### Download ####
- **[mysql](http://dev.mysql.com/downloads/mysql/ "mysql download")**
- **[workbench](http://dev.mysql.com/downloads/tools/workbench/ "workbench download")**

#### linux-installation-debian ####
http://dev.mysql.com/doc/refman/5.6/en/linux-installation-debian.html
#####下载#####
[mysql-5.6.15-debian6.0-x86_64.deb](http://cdn.mysql.com/Downloads/MySQL-5.6/mysql-5.6.15-debian6.0-x86_64.deb)

#####安装#####
	sudo apt-get install libaio-dev
	sudo dpkg -i mysql-5.6.15-debian6.0-x86_64.deb

#####配置#####
http://dev.mysql.com/doc/refman/5.6/en/binary-installation.html

	sudo groupadd mysql
	sudo useradd -r -g mysql mysql
	cd /opt/mysql/server-5.6/
	sudo mkdir data
	sudo chown mysql:mysql data
	sudo mkdir log
	sudo chown mysql:mysql log

#####配置#####
	sudo scripts/mysql_install_db --user=mysql --no-defaults
	sudo cp support-files/my-default.cnf /etc/mysql/my.cnf
	sudo cp support-files/mysql.server /etc/init.d/mysql.server
	sudo cp support-files/mysql-log-rotate /etc/logrotate.d/mysql.server
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

