https://downloads.mariadb.org/mariadb/repositories/
Mint-Mint 14 "Nadia"-5.5
sudo apt-get install software-properties-common
sudo apt-key adv --recv-keys --keyserver keyserver.ubuntu.com 0xcbcb082a1bb943db
sudo add-apt-repository 'deb http://ftp.yz.yamagata-u.ac.jp/pub/dbms/mariadb/repo/5.5/ubuntu quantal main'

sudo apt-get update
sudo apt-get install mariadb-server
