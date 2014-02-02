0.1.JDK
    sudo mkdir /opt/jdk
    sudo scp eric@192.168.0.150:/opt/jdk/jdk-6u38.bin /opt/jdk/jdk-6u38.bin

0.2.MYSQL
    sudo apt-get update
    sudo apt-get install mysql-server
    root/root

0.3.GIT
    sudo apt-get install git

http://gerrit-documentation.googlecode.com/svn/Documentation/2.5.2/index.html
1.1.下载Gerrit Code Review
    sudo axel -n 16 -o /opt/ http://gerrit.googlecode.com/files/gerrit-full-2.5.2.war    

1.2.
    sudo mkdir /opt/gerrit
    sudo nano /opt/gerrit/init_gerrit.sql

    CREATE USER 'gerrit2'@'localhost' IDENTIFIED BY 'secret';
    CREATE DATABASE reviewdb;
    ALTER DATABASE reviewdb CHARACTER SET utf8 COLLATE utf8_general_ci;
    GRANT ALL ON reviewdb.* TO 'gerrit2'@'localhost';
    FLUSH PRIVILEGES;

    mysql -u root -p < /opt/gerrit/init_gerrit.sql
    mysqlshow reviewdb -u gerrit2 -psecret

1.3.用户
    sudo adduser gerrit2
        Adding user `gerrit2' ...
        Adding new group `gerrit2' (1001) ...
        Adding new user `gerrit2' (1001) with group `gerrit2' ...
        Creating home directory `/home/gerrit2' ...
        Copying files from `/etc/skel' ...
    pw=gerrit2
    usermod -g sudo gerrit2
    usermod -G root gerrit2?

2.1.安装
    su gerrit2
    nano ~/.profile
      export JAVA_HOME=/opt/jdk/jdk1.6.0_38
      export PATH=$JAVA_HOME/bin:$PATH
    source ~/.profile

    cd /opt/
    sudo mkdir /opt/review_site
    sudo chown gerrit2:gerrit2 /opt/review_site
    java -jar gerrit-full-2.5.2.war init -d review_site

http://www.infoq.com/cn/articles/Gerrit-jenkins-hudson
2.2.账户    
    ssh-keygen -t rsa -b 2048

http://gerrit-documentation.googlecode.com/svn/Documentation/2.5.2/config-gerrit.html
3.配置    
    sudo nano /opt/review_site/etc/gerrit.config
    [gerrit]
            basePath = git
    [database]
            type = MYSQL
            hostname = localhost
            database = reviewdb
            username = gerrit2
            password=secret
    [auth]
            type = DEVELOPMENT_BECOME_ANY_ACCOUNT
    [sendemail]
            smtpServer = localhost
    [container]
            user = gerrit2
            javaHome = /opt/jdk/jdk1.6.0_38/jre
    [sshd]
            listenAddress = *:29418
    [httpd]
            listenUrl = http://*:8080/
    [cache]
            directory = cache

4.运行
    /opt/review_site/bin/gerrit.sh start
    /opt/review_site/bin/gerrit.sh stop
    /opt/review_site/bin/gerrit.sh restart

cat /opt/review_site/logs/error_log 

    http://gerrit149.com:8080/#/register/
    account: gerrit2
    cat /home/gerrit2/.ssh/id_rsa.pub

    测试:
ssh -p 29418 gerrit2@localhost

    gerrit2@meridians-gerrit:/opt$ mysql -ugerrit2 -psecret
    mysql> show databases;
    +--------------------+
    | Database           |
    +--------------------+
    | information_schema |
    | reviewdb           |
    | test               |
    +--------------------+
    3 rows in set (0.00 sec)

(
5.服务
    sudo ln -snf `pwd`/review_site/bin/gerrit.sh /etc/init.d/gerrit.sh
    sudo ln -snf ../init.d/gerrit.sh /etc/rc3.d/S90gerrit
)

6.项目
    ssh -p 29418 gerrit2@localhost gerrit create-project --name airfactory.git
    git clone ssh://gerrit2@localhost:29418/airfactory.git
    git config remote.origin.push refs/heads/*:refs/for/*

7.Web
    sudo apt-get install highlight gitweb
    git config --file /opt/review_site/etc/gerrit.config gitweb.cgi /usr/lib/cgi-bin/gitweb.cgi
    cat /opt/review_site/etc/gerrit.config

    cat /opt/review_site/etc/gitweb_config.perl

8.Others
Acchout issue:
    Application Error
    Server Error
    Internal Server Error