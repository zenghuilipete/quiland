##top##
> http://www.unixtop.org/man.shtml
display Linux processes

**SYNOPSIS**

	top -hv|-bcHisS -d delay -n limit -u|U user -p pid -w 

- -h | -v : Help/Version
> Show library version and the usage prompt, then quit.

- -b : Batch-mode operation
> Starts top in 'Batch' mode, which could be useful for sending output from top to other programs or to a file. In this mode, top will not accept input and runs until the iterations limit you've set with the '-n' command-line option or until killed.

- -c : Command-line/Program-name toggle
> Starts top with the last remembered 'c' state reversed.
Thus, if top was displaying command lines, now that field
will show program names, and visa versa. See the 'c' inter‐
active command for additional information.

- -d : Delay-time interval as: -d ss.tt (secs.tenths)
> Specifies the delay between screen updates, and overrides the corresponding value in one's personal configuration file or the startup default. Later this can be changed with the 'd' or 's' interactive commands.
> 
Fractional seconds are honored, but a negative number is not allowed. In all cases, however, such changes are prohibited if top is running in 'Secure mode', except for root (unless the 's' command-line option was used). For additional information on 'Secure mode' see topic 6a. SYSTEM Configuration File.

- -H : Threads-mode operation
> Instructs top to display individual threads. Without this command-line option a summation of all threads in each process is shown. Later this can be changed with the 'H' interactive command.

- -i : Idle-process toggle
> Starts top with the last remembered 'i' state reversed. When this toggle is Off, tasks that have not used any CPU since the last update will not be displayed. For additional information regarding this toggle see topic 4c. TASK AREA Commands, SIZE.

- -n : Number-of-iterations limit as: -n number
> Specifies the maximum number of iterations, or frames, top should produce before ending.

- -p : Monitor-PIDs mode as: -pN1 -pN2 ... or -pN1,N2,N3 ...
> Monitor only processes with specified process IDs. This option can be given up to 20 times, or you can provide a comma delimited list with up to 20 pids. Co-mingling both approaches is permitted.
> 
> A pid value of zero will be treated as the process id of the top program itself once it is running.
> 
> This is a command-line option only and should you wish to
return to normal operation, it is not necessary to quit and
and restart top -- just issue any of these interactive com‐
mands: '=', 'u' or 'U'.
> 
> The 'p', 'u' and 'U' command-line options are mutually exclu‐
sive.

- -s : Secure-mode operation
> Starts top with secure mode forced, even for root. This mode is far better controlled through the system configuration file (see topic 6. FILES).

- -S : Cumulative-time toggle
> Starts top with the last remembered 'S' state reversed. When 'Cumulative time' mode is On, each process is listed with the cpu time that it and its dead children have used. See the 'S' interactive command for additional information regarding this mode.

- -u | -U : User-filter-mode as: -u | -U number or name
> Display only processes with a user id or user name matching that given. The '-u' option matches on effective user whereas the '-U' option matches on any user (real, effective, saved, or filesystem).
> 
> The 'p', 'u' and 'U' command-line options are mutually exclu‐
sive.

- -w : Output-width-override as: -w [ number ]
> In 'Batch' mode, when used without an argument top will format output using the COLUMNS= and LINES= environment variables, if set. Otherwise, width will be fixed at the maximum 512 columns. With an argument, output width can be decreased or increased (up to 512) but the number of rows is considered unlimited.
> 
In normal display mode, when used without an argument top will attempt to format output using the COLUMNS= and LINES=environment variables, if set. With an argument, output width can only be decreased, not increased. Whether using environment variables or an argument with -w, when not in 'Batch' mode actual terminal dimensions can never be exceeded.
> 
Note: Without the use of this command-line option, output width is always based on the terminal at which top was invoked whether or not in 'Batch' mode.

**Global-defaults**

    'A' - Alt display Off (full-screen)
    * 'd' - Delay time 3.0 seconds
    * 'H' - Threads mode Off (summarize as tasks)
    'I' - Irix mode On (no, 'solaris' smp)
    * 'p' - PID monitoring Off (show all processes)
    * 's' - Secure mode Off (unsecured)
    'B' - Bold enable On (yes, bold globally)
**Summary-Area-defaults**
	
	'l' - Load Avg/Uptime On (thus program name)
	't' - Task/Cpu states On (1+1 lines, see '1')
	'm' - Mem/Swap usage On (2 lines worth)
	'1' - Single Cpu On (thus 1 line if smp)
**Task-Area-defaults**

	'b' - Bold hilite On (not 'reverse')
	* 'c' - Command line Off (name, not cmdline)
	* 'i' - Idle tasks On (show all tasks)
	'R' - Reverse sort On (pids high-to-low)
	* 'S' - Cumulative time Off (no, dead children)
	* 'u' - User filter Off (show euid only)
	* 'U' - User filter Off (show any uid)
	'x' - Column hilite Off (no, sort field)
	'y' - Row hilite On (yes, running tasks)
	'z' - color/mono Off (no, colors)

**top**
-> **A**

	1:Def - 19:18:00 up 10 days, 40 min,  1 user,  load average: 1.03, 1.04, 1.05
	Threads: 243 total,   2 running, 241 sleeping,   0 stopped,   0 zombie
	%Cpu(s): 50.1 us,  0.3 sy,  0.0 ni, 49.1 id,  0.5 wa,  0.0 hi,  0.0 si,  0.0 st
	KiB Mem:   3071708 total,  1202488 used,  1869220 free,   171272 buffers
	KiB Swap:  3133436 total,        0 used,  3133436 free,   529364 cached

	1  PID USER      PR  NI  VIRT  RES  SHR S  %CPU %MEM    TIME+  COMMAND
	  1535 jenkins   20   0 44792  11m 3528 R  99.9  0.4  14399:09 upgrade-devices
	   171 root      20   0     0    0    0 S   0.3  0.0   0:09.92 jbd2/dm-0-8
	 10906 station   20   0 19484 1684 1100 R   0.3  0.1   0:04.78 top
	     1 root      20   0 26832 2584 1340 S   0.0  0.1   0:01.68 init
	     2 root      20   0     0    0    0 S   0.0  0.0   0:00.01 kthreadd
	2  PID  PPID    TIME+   %CPU %MEM  PR  NI S  VIRT SWAP  RES   UID COMMAND
	 12832  1635   0:01.35   0.0 12.8  20   0 S 2413m    0 383m   106 java
	 10928     2   0:00.00   0.0  0.0  20   0 S     0    0    0     0 kworker/u16:2
	 10919     2   0:00.01   0.0  0.0  20   0 S     0    0    0     0 kworker/u16:0
	 10913     2   0:00.02   0.0  0.0  20   0 S     0    0    0     0 kworker/u16:1
	 10906  7166   0:04.78   0.3  0.1  20   0 R 19484    0 1684  1000 top
	3  PID %MEM  VIRT SWAP  RES CODE DATA  SHR nMaj nDRT S  PR  NI  %CPU COMMAND
	  1636 12.8 2413m    0 383m    4 2.2g  21m    0    0 S  20   0   0.0 java
	  3270 12.8 2413m    0 383m    4 2.2g  21m    0    0 S  20   0   0.0 java
	  3271 12.8 2413m    0 383m    4 2.2g  21m    0    0 S  20   0   0.0 java
	  3272 12.8 2413m    0 383m    4 2.2g  21m    0    0 S  20   0   0.0 java
	  3273 12.8 2413m    0 383m    4 2.2g  21m    0    0 S  20   0   0.0 java
	  3274 12.8 2413m    0 383m    4 2.2g  21m    0    0 S  20   0   0.0 java
	4  PID  PPID   UID USER     RUSER    TTY         TIME+   %CPU %MEM S COMMAND
	  1056     1   105 whoopsie whoopsie ?          0:00.05   0.0  0.1 S whoopsie
	  1058     1   105 whoopsie whoopsie ?          0:00.00   0.0  0.1 S gmain
	  1060     1   105 whoopsie whoopsie ?          0:00.00   0.0  0.1 S gdbus
	   550     1   101 syslog   syslog   ?          1:11.72   0.0  0.1 S rsyslogd
	   555     1   101 syslog   syslog   ?          0:00.66   0.0  0.1 S rs:main Q:Reg
	   558     1   101 syslog   syslog   ?          0:00.11   0.0  0.1 S rsyslogd
