sudo apt-get update
sudo apt-get install mysql-server

sudo nano /etc/mysql/my.cnf
	bind-address=192.168.0.200

mysql -uroot -proot
mysql> grant all privileges on *.* to root@"%" identified by "root" with grant option;
mysql> flush privileges;
mysql> quit

sudo service mysql restart

mysql -V
mysql  Ver 14.14 Distrib 5.5.29, for debian-linux-gnu (x86_64) using readline 6.2
