Install
	http://www.mongodb.org/downloads
	sudo wget http://fastdl.mongodb.org/linux/mongodb-linux-x86_64-2.4.3.tgz -P /opt
	sudo tar -zxvf /opt/mongodb-linux-x86_64-2.4.3.tgz -C /opt
	cd /opt/mongodb-linux-x86_64-2.4.3/
	sudo mkdir data
	sudo ./bin/mongod --dbpath=data

	Please make at least 3379MB available in data/journal or use --smallfiles
	sudo ./bin/mongod --dbpath=data --smallfiles

Test
	/opt/mongodb-linux-x86_64-2.4.3/bin/mongo
	> show dbs
	> use local
	> show collections