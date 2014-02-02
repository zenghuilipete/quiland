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