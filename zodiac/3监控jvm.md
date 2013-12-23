##jstat(Java Virtual Machine Statistics Monitoring Tool)##
获得pid

	jps
	netstat -lpn|grep 8080 | awk {'print $7}'| awk -F '/' {'print $1'}
	ps -ef | grep tomcat

打印加载class的数量和占用的空间

	jstat -class 13669
	Loaded  Bytes  Unloaded  Bytes     Time
	 16723 34757.7       66    97.4      12.87

打印VM实时编译的数量

	jstat -compiler 13669
	Compiled Failed Invalid   Time   FailedType FailedMethod
	    2981      1       0    62.48          1 com/intellij/util/lang/ClasspathCache nameSymbolsLoaded

打印gc的次数和时间

	jstat -gc 13669
	 S0C    S1C    S0U    S1U      EC       EU        OC         OU       PC     PU    YGC     YGCT    FGC    FGCT     GCT
	5184.0 5184.0  0.0   2777.7 42048.0  28641.2   104572.0   43358.9   150360.0 90817.6     55    1.229   6      0.575    1.804

## jmap(Memory Map)##
打印堆快照

	jmap -J-d64 -heap 3219
	Attaching to process ID 3219, please wait...
	Debugger attached successfully.
	Server compiler detected.
	JVM version is 24.45-b08
	
	using thread-local object allocation.
	Parallel GC with 2 thread(s)
	
	Heap Configuration:
	   MinHeapFreeRatio = 40
	   MaxHeapFreeRatio = 70
	   MaxHeapSize      = 2063597568 (1968.0MB)
	   NewSize          = 1310720 (1.25MB)
	   MaxNewSize       = 17592186044415 MB
	   OldSize          = 5439488 (5.1875MB)
	   NewRatio         = 2
	   SurvivorRatio    = 8
	   PermSize         = 21757952 (20.75MB)
	   MaxPermSize      = 85983232 (82.0MB)
	   G1HeapRegionSize = 0 (0.0MB)
	
	Heap Usage:
	PS Young Generation
	Eden Space:
	   capacity = 62914560 (60.0MB)
	   used     = 8781384 (8.374580383300781MB)
	   free     = 54133176 (51.62541961669922MB)
	   13.957633972167969% used
	From Space:
	   capacity = 5242880 (5.0MB)
	   used     = 5211960 (4.970512390136719MB)
	   free     = 30920 (0.02948760986328125MB)
	   99.41024780273438% used
	To Space:
	   capacity = 17825792 (17.0MB)
	   used     = 0 (0.0MB)
	   free     = 17825792 (17.0MB)
	   0.0% used
	PS Old Generation
	   capacity = 85983232 (82.0MB)
	   used     = 34157928 (32.575538635253906MB)
	   free     = 51825304 (49.424461364746094MB)
	   39.726266628358424% used
	PS Perm Generation
	   capacity = 22020096 (21.0MB)
	   used     = 21904784 (20.890029907226562MB)
	   free     = 115312 (0.1099700927734375MB)
	   99.47633289155506% used
	
	11945 interned Strings occupying 1659968 bytes.

打印活动的class实例直方图

	jmap -J-d64 -histo:live 3219 > jmap.histo.txt

dump快照

	jmap -dump:format=b,file=mem.bin 3219
	sudo scp root@server:/home/erichan/mem.bin /var/local/my.mem.bin
	jhat -J-Xmx4096m -port 7000 my.mem.bin
	http://localhost:7000/showInstanceCounts/

## VisualVM_remote ##
The remote system must be configured to run the **jstatd daemon**.
http://docs.oracle.com/javase/7/docs/technotes/tools/share/jstatd.html

### Server(Ubuntu) ###
1.nano /home/hanl/feu_vm.policy

	grant codebase "file:${java.home}/../lib/tools.jar" {
	   permission java.security.AllPermission;
	};

2.run and don't wait pending

	jstatd -J-Djava.security.policy=/home/hanl/feu_vm.policy

	sudo nano /etc/hosts
	10.11.58.184 hanl-ubuntu1204
	
	hostname -i
	10.11.58.184
	
### Client(Windows) ###
	jps hanl-ubuntu1204
	3177 Jstatd

## jinfo ##
打印虚拟机参数值

	jinfo -J-d64 -flag MaxPermSize 3219
	-XX:MaxPermSize=85983232
	
打印系统环境变量

	jinfo -J-d64 -sysprops 3219
	...
	JVM version is 24.45-b08
	java.runtime.name = Java(TM) SE Runtime Environment
	java.vm.version = 24.45-b08
	sun.boot.library.path = /usr/local/aquarius/jdk1.7.0_45/jre/lib/amd64
	os.version = 3.11.0-14-generic
	java.vm.specification.version = 1.7
	sun.arch.data.model = 64
	sun.java.command = org.apache.catalina.startup.Bootstrap start
	java.home = /usr/local/aquarius/jdk1.7.0_45/jre
	java.vm.info = mixed mode
	java.version = 1.7.0_45
	java.ext.dirs = /usr/local/aquarius/jdk1.7.0_45/jre/lib/ext:/usr/java/packages/lib/ext
	common.loader = "${catalina.base}/lib","${catalina.base}/lib/*.jar","${catalina.home}/lib","${catalina.home}/lib/*.jar"


