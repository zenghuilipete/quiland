1.安装
1.1.UBUNTU安装
wget -q -O - http://pkg.jenkins-ci.org/debian/jenkins-ci.org.key | sudo apt-key add -
sudo sh -c 'echo deb http://pkg.jenkins-ci.org/debian binary/ > /etc/apt/sources.list.d/jenkins.list'
sudo apt-get update
sudo apt-get install jenkins
--
1.2.Java Web Archive安装(150使用的是这种方式)
sudo wget http://mirror.xmission.com/jenkins/war/1.500/jenkins.war -P /opt/
sudo cp /opt/jenkins.war /opt/tomcat7.0.34/webapps/

2.配置
sudo nano /opt/tomcat7.0.34/conf/server.xml
	<Connector ... URIEncoding="UTF-8"/>

sudo nano /opt/tomcat7.0.34/bin/catalina.sh
	JAVA_OPTS="-server -Xms1024m -Xmx1024m"
	export JAVA_HOME=/opt/jdk/jdk1.6.0_38
	export JENKINS_HOME=/opt/jenkins_home

sudo chown -R eric:eric /opt/jenkins_home
sudo chown -R eric:eric /opt/tomcat7.0.34

sudo nano /opt/tomcat7.0.34/conf/tomcat-users.xml
	<role rolename="admin-gui"/>
	<role rolename="manager-gui"/>
	<user username="eric" password="han" roles="admin-gui"/>
	<user username="eric" password="han" roles="manager-gui"/>

3.启动(不要使用sudo! root身份找不到eric的.ssh/config)
/opt/tomcat7.0.34/bin/startup.sh

(4.TOMCAT自启动(不要使用sudo!)
参考 5.tomcat安装.txt
/etc/init.d/tomcat7 start
sudo update-rc.d -f tomcat remove

问题：
java.util.concurrent.ExecutionException: hudson.util.IOException2: Failed to create a temporary file in /opt/jenkins_home/fingerprints
解决：
sudo chown -R eric:eric /opt/jenkins_home
--
sudo update-rc.d -f tomcat7 remove
)

5.Maven
参考 4.maven安装.txt

6.安装GIT
sudo apt-get install git

7.PlugIn

7.1.Plugin List
	GitHub plugin: https://wiki.jenkins-ci.org/display/JENKINS/Github+Plugin

7.2.Scheduled Build Jobs
	MINUTE HOUR DOM MONTH DOW

	MINUTE
	Minutes within the hour (0–59)
	HOUR
	The hour of the day (0–23) DOM
	DOM
	The day of the month (1–31)
	MONTH
	The month (1–12)
	DOW
	The day of the week (0–7) where 0 and 7 are Sunday.

	There are also a few short-cuts:
	• “*” represents all possible values for a field. For example, “* * * * *” means “once a minute.”
	• You can define ranges using the “M–N” notation. For example “1-5” in the DOW field would mean “Monday to Friday.”
	• You can use the slash notation to defined skips through a range. For example, “*/5” in the MINUTE field would mean “every five minutes.”
	• A comma-separated list indicates a list of valid values. For example, “15,45” in the MINUTE field would mean “at 15 and 45 minutes past every hour.”
	• You can also use the shorthand values of “@yearly”, “@annually”, “@monthly”, “@weekly”, “@daily”, “@midnight”, and “@hourly”.

7.3.Deploying to Nexus
![slave151](picture/deploy_nexus.png)
Repository URL=http://192.168.0.130:8081/nexus/content/repositories/snapshots/	
Repository ID=nexus-snapshots
