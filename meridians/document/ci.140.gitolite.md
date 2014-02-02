1.SSH设置
1.1.[server:meridians-gitolite]安装openssh-server
sudo apt-get install openssh-server

1.2.[client:hanl-ubuntu1204]测试连接
eric@meridians-jenkins-master:~$ ssh eric@192.168.0.140
	The authenticity of host '192.168.0.140 (192.168.0.140)' can't be established.
	ECDSA key fingerprint is e3:28:c5:b0:82:da:47:2f:34:30:bd:11:95:22:4f:4d.
	Are you sure you want to continue connecting (yes/no)? yes
	Warning: Permanently added '192.168.0.140' (ECDSA) to the list of known hosts.
	eric@192.168.0.140's password:
	Welcome to Ubuntu 12.10 (GNU/Linux 3.5.0-17-generic x86_64)

	 * Documentation:  https://help.ubuntu.com/
	Last login: Sat Feb  2 05:43:10 2013 from 192.168.0.102
eric@meridians-gitolite:~$ exit

ssh-keygen -f "/home/eric/.ssh/known_hosts" -R 192.168.0.140

2.登录git server
hanl@hanl-ubuntu1204:~$ ssh eric@192.168.0.140

2.1.安装GIT
sudo apt-get install git
配置
git config --global user.name "Eric Han"
git config --global user.email  "feuyeux@gmail.com"
验证
git config -l
	user.name=Eric Han
	user.email=feuyeux@gmail.com

(2.2.GIT代理)
2.2.1.安装
sudo apt-get install socat
2.2.2.创建socat脚本
cd /home/git
sudo nano git-proxy.sh
	#!/bin/bash
	socat STDIO PROXY:10.41.255.9:$1:$2,proxyport=80
sudo chmod 777 git-proxy.sh
2.2.3.配置git代理
sudo nano /etc/profile
	export GIT_PROXY_COMMAND=/home/git/git-proxy.sh
source /etc/profile

2.3.创建用户git
sudo adduser git
添加到sudo组
sudo usermod -G sudo -a git

2.4.使用git身份
su git
cd ~

2.5.安装gitolite
sudo git clone git://github.com/sitaramc/gitolite /opt/gitolite-src
mkdir -p ~/bin
/opt/gitolite-src/install -to ~/bin
nano .bashrc
	PATH=/home/git/bin:$PATH
	ctrl+o ctrl+x
source .bashrc

2.6.测试
gitolite help
hello, this is gitolite3 v3.3-10-g293df79 on git 1.7.10.4

2.7.退出git帐号 退出SSH登录
exit
exit

3.SSH证书
3.1.生成公私钥
hanl@hanl-ubuntu1204:~$ ssh-keygen -f ~/.ssh/han

3.2.拷贝证书到git server
sudo scp ~/.ssh/han.pub git@192.168.0.140:/tmp/han.pub
sudo scp /home/eric/.ssh/jenkins.pub git@192.168.0.140:/tmp/jenkins.pub

3.3.安装证书到gitolite
ssh git@192.168.0.140
gitolite setup -pk /tmp/han.pub
Initialized empty Git repository in /home/git/repositories/gitolite-admin.git/
Initialized empty Git repository in /home/git/repositories/testing.git/
WARNING: /home/git/.ssh/authorized_keys missing; creating a new one

3.4.退出SSH登录
exit

4.配置SSH无密码登录
hanl@hanl-ubuntu1204:~$ sudo nano ~/.ssh/config
	# gitolite server
	host gito
	user git
	hostname 192.168.0.140
	port 22
	IdentityFile ~/.ssh/han

5.克隆GITolite管理
hanl@hanl-ubuntu1204:~$ git clone gito:gitolite-admin.git
Cloning into 'gitolite-admin'...
remote: Counting objects: 6, done.
remote: Compressing objects: 100% (4/4), done.
remote: Total 6 (delta 0), reused 0 (delta 0)
Receiving objects: 100% (6/6), done.

hanl@hanl-ubuntu1204:~$ cat gitolite-admin/conf/gitolite.conf
	repo gitolite-admin
	    RW+     =   han
	repo testing
	    RW+     =   @all

6.管理GIT项目
6.1编辑
hanl@hanl-ubuntu1204:~$ nano gitolite-admin/conf/gitolite.conf
	repo gitolite-admin
	    RW+     =   han
	repo testing
	    RW+     =   @all
	repo airfactory
		RW+		=	@all
6.2.提交
hanl@hanl-ubuntu1204:~$ cd gitolite-admin/
git commit -a -m "add airfactory repo"
git push

6.3.验证
ssh git@192.168.0.140
git@utfteam-virtual-machine:~$ ls repositories/
	airfactory.git  gitolite-admin.git  testing.git
exit
6.4.检出
hanl@hanl-ubuntu1204:~$ git clone gito:airfactory
	Cloning into 'airfactory'...
	warning: You appear to have cloned an empty repository.

7. web展示
7.1安装gitweb
[192.168.0.140]
sudo apt-get install highlight gitweb
	The following extra packages will be installed:
	  apache2 apache2-mpm-worker apache2-utils apache2.2-bin apache2.2-common highlight-common libapr1 libaprutil1
	  libaprutil1-dbd-sqlite3 libaprutil1-ldap
	Suggested packages:
	  apache2-doc apache2-suexec apache2-suexec-custom httpd-cgi libcgi-fast-perl git-doc
	The following NEW packages will be installed:
	  apache2 apache2-mpm-worker apache2-utils apache2.2-bin apache2.2-common gitweb highlight highlight-common
	  libapr1 libaprutil1 libaprutil1-dbd-sqlite3 libaprutil1-ldap
	0 upgraded, 12 newly installed, 0 to remove and 210 not upgraded.
	Need to get 2,371 kB of archives.

(gitweb的配置文件在/usr/share/gitweb中，cgi文件存放在 /usr/lib/cgi-bin下
ln -sf /usr/share/gitweb /home/eric/gitweb)

sudo nano /etc/apache2/apache2.conf
	append:
	ServerName 127.0.0.1

sudo nano /etc/gitweb.conf
	edit:
	$projectroot = "/home/git/repositories";
	$home_link = $my_uri || "/";
	$projects_list = $projectroot;
	@stylesheets = ("/static/gitweb.css");
	$javascript = "static/gitweb.js";
	$logo = "/static/git-logo.png";
	$favicon = "/static/git-favicon.png";

	append:
	$feature{'highlight'}{'default'} = [1];
sudo chmod 755 /home/git

组可读
sudo chmod g+r /home/git/projects.list
(chmod 755 /home/git/projects.list)

sudo chmod -R g+rx /home/git/repositories
（chmod -R 777 /home/git/repositories）

sudo nano /home/git/.gitolite.rc
	umask 0000

	权限掩码umask共为4位:gid/uid,属主，组权，其它用户的权限
	通常用到的是后3个
		Octal value : Permission
		0 : read, write and execute
		1 : read and write
		2 : read and execute
		3 : read only
		4 : write and execute
		5 : write only
		6 : execute only
		7 : no permissions

sudo /etc/init.d/apache2 restart
(sudo service apache2 restart)

http://192.168.0.140/gitweb/

nano gitolite-admin/conf/gitolite.conf
	append:
	repo gitweb
	    RW+     =   @all
hanl@hanl-ubuntu1204:~/gitolite-admin$ git commit -a -m "add gitweb repo"
git push

7.2cgit
安装
sudo apt-get install make
sudo apt-get update
sudo apt-get install gcc
sudo apt-get install libssl-dev

git clone git://hjemli.net/pub/git/cgit
cd /home/eric/cgit
git submodule init
git submodule update
make
sudo make install
	    SUBDIR git
	    SUBDIR git
	    CC cgit
	install -m 0755 -d /var/www/htdocs/cgit
	install -m 0755 cgit /var/www/htdocs/cgit/cgit.cgi
	install -m 0755 -d /var/www/htdocs/cgit
	install -m 0644 cgit.css /var/www/htdocs/cgit/cgit.css
	install -m 0644 cgit.png /var/www/htdocs/cgit/cgit.png
	install -m 0755 -d /usr/lib/cgit/filters
	install -m 0755 filters/* /usr/lib/cgit/filters

sudo nano /etc/apache2/sites-available/default
	ScriptAlias /cgit/ /var/www/htdocs/cgit/
	<Directory "/var/www/htdocs/cgit">
	    AllowOverride None
	    Options +ExecCGI
	    Order allow,deny
	    Allow from all
	</Directory>

sudo nano /etc/cgitrc
	enable-gitweb-owner=1
	project-list=/home/git/projects.list
	scan-path=/home/git/repositories/
	root-readme=/var/www/htdocs/about.html
	css=/cgit.css
	logo=/cgit.png
	enable-gitweb-owner=1
	project-list=/home/git/projects.list
	scan-path=/home/git/repositories/

/var/www/htdocs/cgit/cgit.png

7.3GitList
https://github.com/klaussilveira/gitlist