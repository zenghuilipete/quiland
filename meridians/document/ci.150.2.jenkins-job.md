1.[jenkins 192.168.0.150]
1.1设置git全局变量
git config --global user.name "Eric Han"
git config --global user.email  "feuyeux@gmail.com"

1.2从github取项目
git://github.com/feuyeux/airfactory.git
物理地址
/opt/jenkins_home/jobs/airfactory/workspace

1.3创建gitolite新项目airfactory
git clone gito:gitolite-admin
nano gitolite-admin/conf/gitolite.conf
	repo gitolite-admin
	    RW+     =   git
	    RW+     =   jenkins
	repo airfactory
	    RW+     =   @all
	repo gitweb
	    RW+     =   @all
	repo testing
	    RW+     =   @all

1.4
git clone gito:airfactory
git add -A
eric@meridians-jenkins-master:~/airfactory$ git commit -m "start air"
(git commit -a -m "start air")
git push origin master

1.5
[windows]
D:\-capricorn>git clone gito:airfactory

2.jenkins job
http://192.168.0.150:8080/jenkins/job/airfactory/configure
Project name
	airfactory
Source Code Management
	Repository URL
		ssh://gito/airfactory

		~/.ssh/config
		----------------------------------------
		Host [repository host name]
		    User gitolite
		    HostName [host name]
		    IdentityFile [identity file pash]
		----------------------------------------
		Repository URL	ssh://[repository host name]/[repo name]
		Name	jenkins
	Branch Specifier
		master
Build
	Root POM
		pom.xml
	Goals and options
		install

Build Triggers/构建触发器
	Trigger builds remotely/触发远程构建
		Authentication Token/身份验证令牌
			from140

3.hook
[gitolite 192.168.0.140]
sudo nano ~/repositories/airfactory.git/hooks/post-receive
	#!/bin/bash
	/usr/bin/curl --user eric:han -s \
	http://192.168.0.150:8080/jenkins/job/airfactory/build?token=from140
