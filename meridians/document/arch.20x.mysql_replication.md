主从复制

master
10.11.58.238
slave
10.11.58.243

::master::
sudo cp /etc/mysql/my.cnf /etc/mysql/master.cnf
sudo nano /etc/mysql/master.cnf
	log-bin=mysql-bin #slave会基于此log-bin来做replication
	server-id=1 #master的标示 
	innodb_flush_log_at_trx_commit=1  
	sync_binlog=1

//mysqladmin -u root -p shutdown
sudo service mysql stop
sudo mysqld_safe --defaults-file=/etc/mysql/master.cnf & 

mysql -uroot -proot
mysql> SHOW MASTER STATUS;
+------------------+----------+--------------+------------------+
| File             | Position | Binlog_Do_DB | Binlog_Ignore_DB |
+------------------+----------+--------------+------------------+
| mysql-bin.000003 |      107 |              |                  |
+------------------+----------+--------------+------------------+
1 row in set (0.00 sec)

#grant replication slave on *.* to root@10.11.58.243 identified by 'root';
mysql> use mysql;
mysql> select host, user, password from user;
+------------------+------------------+-------------------------------------------+
| host             | user             | password                                  |
+------------------+------------------+-------------------------------------------+
| localhost        | root             | *81F5E21E35407D884A6CD4A731AEBFB6AF209E1B |
| meridians-mysql1 | root             | *81F5E21E35407D884A6CD4A731AEBFB6AF209E1B |
| 127.0.0.1        | root             | *81F5E21E35407D884A6CD4A731AEBFB6AF209E1B |
| ::1              | root             | *81F5E21E35407D884A6CD4A731AEBFB6AF209E1B |
| localhost        |                  |                                           |
| meridians-mysql1 |                  |                                           |
| localhost        | debian-sys-maint | *0D11DCDCF0DD1823F51BFEA706D0B21480878DC6 |
| 10.11.58.243     | root             | *81F5E21E35407D884A6CD4A731AEBFB6AF209E1B |
+------------------+------------------+-------------------------------------------+
8 rows in set (0.00 sec)

::slave::
sudo cp /etc/mysql/my.cnf /etc/mysql/slave.cnf
sudo nano /etc/mysql/slave.cnf
sudo service mysql stop
sudo mysqld_safe --defaults-file=/etc/mysql/slave.cnf & 
mysql -uroot -proot

mysql> START SLAVE;
ERROR 1201 (HY000): Could not initialize master info structure; more error messages can be found in the MySQL error log

mysql> slave stop;
mysql> reset slave;
mysql> change master to master_host='10.11.58.238', master_user='root', master_password='root', master_log_file='mysql-bin.000001', master_log_pos=259;
mysql>  slave start;

mysql> show slave status \G;
...
            Slave_IO_Running: Yes
            Slave_SQL_Running: Yes
...


mysql> show processlist\G;

测试
::master::
mysql> use test;
mysql> show tables;
Empty set (0.00 sec)

mysql> CREATE TABLE `test1`(`testId` int(10) unsigned NOT NULL auto_increment, `testName` varchar(15) NOT NULL, PRIMARY KEY (`testId`)) ENGINE=InnoDB;
mysql> INSERT INTO test1 VALUES ('1', 'Active');
mysql> INSERT INTO test1 VALUES ('2', 'Disabled');
mysql> INSERT INTO test1 VALUES ('3', 'Deleted');

::slave::
mysql> use test;
mysql> show tables;
+----------------+
| Tables_in_test |
+----------------+
| test1          |
+----------------+
1 row in set (0.00 sec)

mysql> select * from test1;
+--------+----------+
| testId | testName |
+--------+----------+
|      1 | Active   |
|      2 | Disabled |
|      3 | Deleted  |
+--------+----------+
3 rows in set (0.00 sec)

----
异步复制与同步复制 
异步复制：MySQL本身支持单向的、异步的复制。异步复制意味着在把数据从一台机器拷贝到另一台机器时有一个延时 – 最重要的是这意味着当应用系统的事务提交已经确认时数据并不能在同一时刻拷贝/应用到从机。通常这个延时是由网络带宽、资源可用性和系统负载决定的。然而，使用正确的组件并且调优，复制能做到接近瞬时完成。 
同步复制：同步复制可以定义为数据在同一时刻被提交到一台或多台机器，通常这是通过众所周知的“两阶段提交”做到的。虽然这确实给你在多系统中保持一致性，但也由于增加了额外的消息交换而造成性能下降。 
使用MyISAM或者InnoDB存储引擎的MySQL本身并不支持同步复制，然而有些技术，例如分布式复制块设备（简称DRBD），可以在下层的文件系统提供同步复制，允许第二个MySQL服务器在主服务器丢失的情况下接管（使用第二服务器的复本）。要了解更多信息，请参见：http://www.drbd.org/
