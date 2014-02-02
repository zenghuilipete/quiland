Install
	http://redis.io/download
	sudo wget http://redis.googlecode.com/files/redis-2.6.13.tar.gz -P /opt
	sudo tar zxvf /opt/redis-2.6.13.tar.gz -C /opt
	cd /opt/redis-2.6.13/
	sudo make

Running Redis
	https://github.com/antirez/redis
	cd /opt/redis-2.6.13/src/
    ./redis-server

Playing with Redis
	cd /opt/redis-2.6.13/src/
	/opt/redis-2.6.13/src $ ./redis-cli
	redis 127.0.0.1:6379> ping
	PONG
	redis 127.0.0.1:6379> set foo bar
	OK
	redis 127.0.0.1:6379> get foo
	"bar"
	redis 127.0.0.1:6379>