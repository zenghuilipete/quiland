## MINA2.0.7 ##

### 基于MINA应用的架构 ###
基于TCP/IP协议,UDP/IP协议,VM管道通信,串行通信(RS-232C)RXTX 

![Mina App Arch](http://mina.apache.org/staticresources/images/mina/apparch_small.png) 

### 服务器 ###
![](http://mina.apache.org/staticresources/images/mina/Server_arch.png)

### 客户端 ###

![](http://mina.apache.org/staticresources/images/mina/clientdiagram.png)

### MINA组件 ###
![Mina Components](http://mina.apache.org/staticresources/images/mina/mina_app_arch.png)

#### MINA3层组件 ####

##### - I/O Service：处理I/O操作 #####

![IoService Mind Map](http://mina.apache.org/staticresources/images/mina/IoService_mindmap.png)

##### - I/O Filter Chain：过滤/转换 数据和字节 #####

##### - I/O Handler：处理业务逻辑 #####
- sessionCreated
- sessionOpened
- sessionClosed
- sessionIdle
- exceptionCaught
- messageReceived
- messageSent

### IoService ###
#### IoAcceptor ####
- NioSocketAcceptor : the non-blocking Socket transport IoAcceptor
- NioDatagramAcceptor : the non-blocking UDP transport IoAcceptor
- AprSocketAcceptor : the blocking Socket transport IoAcceptor, based on APR
- VmPipeSocketAcceptor : the in-VM IoAcceptor

![](http://mina.apache.org/staticresources/images/mina/IoServiceAcceptor.png)

#### IoConnector ####
- NioSocketConnector : the non-blocking Socket transport IoConnector
- NioDatagramConnector : the non-blocking UDP transport IoConnector
- AprSocketConnector : the blocking Socket transport IoConnector, based on APR
- ProxyConnector : a IoConnector providing proxy support
- SerialConnector : a IoConnector for a serial transport
- VmPipeConnector : the in-VM IoConnector

![](http://mina.apache.org/staticresources/images/mina/IoServiceConnector.png)

### Session ###
![](http://mina.apache.org/staticresources/images/mina/session-state.png)

### Filters ###

### Transports ###
- APR Transport
- Serial Transport

### IoBuffer ###

### Codec Filter ###

### Logging Filter ###

### State Machine ###
![](http://mina.apache.org/staticresources/images/mina/state-diagram.png)

[Source code](git clone http://git-wip-us.apache.org/repos/asf/mina.git mina)
[MINA user guide](http://mina.apache.org/mina-project/userguide/user-guide-toc.html)