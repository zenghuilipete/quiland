ps aux | grep sonar
ps -ef | grep `ssh`
ps axo pid,user,stat,command|grep -v grep |grep davmail

ps - report a snapshot of the current processes.

DESCRIPTION
Note that "ps -aux" is distinct from "ps aux". 
The POSIX and UNIX standards require that "ps -aux" print all processes owned by a user named "x", as well as printing all processes that would be selected by the -a option. If the user named "x" does not exist, this ps may interpret the command as "ps aux" instead and print a warning. This behavior is intended to aid in transitioning old scripts and habits. It is fragile, subject to change, and thus should not be relied upon.

By default, ps selects all processes with the same effective user ID (euid=EUID) as the current user and associated with the same terminal as the invoker. It displays the process ID (pid=PID), the terminal associated with the process (tname=TTY), the cumulated CPU time in [DD-]hh:mm:ss format (time=TIME), and the executable name (ucmd=CMD). Output is unsorted by default.

EXAMPLES
       To see every process on the system using standard syntax:
          ps -e
          ps -ef
          ps -eF
          ps -ely

       To see every process on the system using BSD syntax:
          ps ax
          ps axu

       To print a process tree:
          ps -ejH
          ps axjf

       To get info about threads:
          ps -eLf
          ps axms

       To get security info:
          ps -eo euser,ruser,suser,fuser,f,comm,label
          ps axZ
          ps -eM

       To see every process running as root (real & effective ID) in user format:
          ps -U root -u root u

       To see every process with a user-defined format:
          ps -eo pid,tid,class,rtprio,ni,pri,psr,pcpu,stat,wchan:14,comm
          ps axo stat,euid,ruid,tty,tpgid,sess,pgrp,ppid,pid,pcpu,comm
          ps -eopid,tt,user,fname,tmout,f,wchan

       Print only the process IDs of syslogd:
          ps -C syslogd -o pid=

       Print only the name of PID 42:
          ps -p 42 -o comm=


USER      进程的属主；
PID       进程的ID；
PPID 父进程；
%CPU      进程占用的CPU百分比；
%MEM      占用内存的百分比；
NI           进程的NICE值，数值大，表示较少占用CPU时间；
VSZ 进程虚拟大小；
RSS 驻留中页的数量；
WCHAN
TTY 终端ID
STAT 进程状态

D    不可中断 Uninterruptible sleep (usually IO)
R    正在运行可中在队列中可过行的；
S    处于休眠状态；
T    停止或被追踪；
W    进入内存交换（从内核2.6开始无效）；
X    死掉的进程（从来没见过）；
Z    僵尸进程；

N    优先级较低的进程
L    有些页被锁进内存；
s    进程的领导者（在它之下有子进程）；
l    is multi-threaded (using CLONE_THREAD, like NPTL pthreads do)
+                 位于后台的进程组；

WCHAN     正在等待的进程资源
START 启动进程的时间
TIME      进程消耗CPU的时间
COMMAND 命令的名称和参数