# ==ActiveMQ== #

### 0 [Web Browsing of git](http://activemq.apache.org/source.html) ###
https://git-wip-us.apache.org/repos/asf?p=activemq.git
### 1 Checking out from git ###
git clone https://git-wip-us.apache.org/repos/asf/activemq.git

- sudo wget http://mirror.bjtu.edu.cn/apache/activemq/apache-activemq/5.8.0/apache-activemq-5.8.0-bin.tar.gz -P /opt
- sudo tar -xzf /opt/apache-activemq-5.8.0-bin.tar.gz -C /opt
 
### 2 [Windows Source Installation](http://activemq.apache.org/version-5-getting-started.html)  ###
- cd [activemq_install_dir]
- mvn clean install -Dmaven.test.skip=true
- cd [activemq_install_dir]\assembly\target
- unzip activemq-x.x-SNAPSHOT.zip
- cd activemq-x.x-SNAPSHOT
- bin\activemq

### 3 Starting ActiveMQ ###
bin\activemq start
> By default ActiveMQ uses the conf/activemq.xml as the main configuration file when starting. 

- bin/activemq console xbean:conf/activemq-demo.xml
- bin/activemq start xbean:conf/activemq-demo.xml
- sudo /opt/apache-activemq-5.8.0/bin/activemq start xbean:/home/eric/[activemq.xml](arch.188activemq.xml)

### 4 Testing the Installation ###
- netstat -an|find "61616"
- netstat -an|grep 61616
- telnet 192.168.0.188 61616
#### 5 Monitoring ActiveMQ ####
http://localhost:8161/hawtio

### 6 Stopping ActiveMQ ###
bin/activemq stop

### 7 [activemq-command-line-tools-reference](http://activemq.apache.org/activemq-command-line-tools-reference.html) ###

### Queue ###
D:\codes\activemq-in-action\examples\src\main\java\org\apache\activemq\book\ch2\jobs\Producer.java
D:\codes\activemq-in-action\examples\src\main\java\org\apache\activemq\book\ch2\jobs\Consumer.java
### Topic ###