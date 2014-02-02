# ==Apollo== #
## 1.Download ##


- http://www.apache.org/dyn/closer.cgi?path=activemq/activemq-apollo/1.6/apache-apollo-1.6-unix-distro.tar.gz
- sudo axel -n 10 -a -S5 http://apache.01link.hk/activemq/activemq-apollo/1.6/apache-apollo-1.6-unix-distro.tar.gz -o /opt
- sudo wget http://apache.01link.hk/activemq/activemq-apollo/1.6/apache-apollo-1.6-unix-distro.tar.gz -P /opt

## 2.Install ##
sudo tar -xzf /opt/apache-apollo-1.6-unix-distro.tar.gz -C /opt

## 3.Generate Broker ##
`/opt/apache-apollo-1.6/bin/apollo create mybroker`

	Creating apollo instance at: mybroker
	Generating ssl keystore...

	You can now start the broker by executing:
	   "/opt/apache-apollo-1.6/bin/mybroker/bin/apollo-broker" run

	Or you can setup the broker as system service and run it in the background:
	   sudo ln -s "/opt/apache-apollo-1.6/bin/mybroker/bin/apollo-broker-service" /etc/init.d/
	   /etc/init.d/apollo-broker-service start


> A broker instance directory will contain the following sub directories:
> 
    bin: holds execution scripts associated with this instance.
    etc: hold the instance configuration files
    data: holds the data files used for storing persistent messages
    log: holds rotating log files
    tmp: holds temporary files that are safe to delete between broker runs

## 4.Run broker ##

- centOS: "/home/eric/mybroker/bin/apollo-broker" run
- "/opt/apache-apollo-1.6/bin/mybroker/bin/apollo-broker" run 

WEB:

- http://192.168.0.188:61680/
- https://127.0.0.1:61681/

> The default login id and password is admin and password.

## 5.Configure broker ##
http://activemq.apache.org/apollo/documentation/user-manual.html

-  sudo nano /home/eric/mybroker/etc/apollo.xml
-  sudo nano /opt/apache-apollo-1.6/bin/mybroker/etc/apollo.xml
-  **sudo chkconfig --level 35 iptables off**
-  **/etc/init.d/iptables stop**

## 6.source code(scala) ##
https://github.com/chirino/activemq-apollo