##vmstat##
**vmstat -V**

	vmstat from procps-ng 3.3.3

**vmstat**

	procs -----------memory---------- ---swap-- -----io---- -system-- ----cpu----
	r  b   swpd   free   buff  cache   si   so    bi    bo   in   cs us sy id wa
	3  0      0 1866608 171260 529212    0    0     0     2   13    9 50  0 50  0

**FIELD DESCRIPTION FOR VM MODE**

- Procs
	- r: The number of processes waiting for run time.
	- b: The number of processes in uninterruptible sleep.

- Memory
	- swpd: the amount of virtual memory used.
	- free: the amount of idle memory.
	- buff: the amount of memory used as buffers.
	- cache: the amount of memory used as cache.
	- inact: the amount of inactive memory.  (-a option)
	- active: the amount of active memory.  (-a option)

- Swap
	- si: Amount of memory swapped in from disk (/s).
	- so: Amount of memory swapped to disk (/s).

- IO
	- bi: Blocks received from a block device (blocks/s).
	- bo: Blocks sent to a block device (blocks/s).

- System
	- in: The number of interrupts per second, including the clock.
	- cs: The number of context switches per second.

- CPU
	- These are percentages of total CPU time.
	- us: Time spent running non-kernel code.  (user time, including nice time)
	- sy: Time spent running kernel code.  (system time)
	- id: Time spent idle.  Prior to Linux 2.5.41, this includes IO-wait time.
	- wa: Time spent waiting for IO.  Prior to Linux 2.5.41, included in idle.
	- st: Time stolen from a virtual machine.  Prior to Linux 2.6.11, unknown.

## vmstat -s ##
 
     3071708 K total memory
      1205192 K used memory
       936792 K active memory
       172884 K inactive memory
      1866516 K free memory
       171260 K buffer memory
       529212 K swap cache
      3133436 K total swap
            0 K used swap
      3133436 K free swap
     82127041 non-nice user cpu ticks
        19101 nice user cpu ticks
        51175 system cpu ticks
     81741235 idle cpu ticks
       354798 IO-wait cpu ticks
            0 IRQ cpu ticks
          474 softirq cpu ticks
            0 stolen cpu ticks
       434939 pages paged in
      3613221 pages paged out
            0 pages swapped in
            0 pages swapped out
    236819934 interrupts
     58109448 CPU context switches
	1386931050 boot time
        39786 forks

## vmstat -d ##

	disk- ------------reads------------ ------------writes----------- -----IO------
	       total merged sectors      ms  total merged sectors      ms    cur    sec
	ram0       0      0       0       0      0      0       0       0      0      0
	ram1       0      0       0       0      0      0       0       0      0      0
	ram2       0      0       0       0      0      0       0       0      0      0
	ram3       0      0       0       0      0      0       0       0      0      0
	ram4       0      0       0       0      0      0       0       0      0      0
	ram5       0      0       0       0      0      0       0       0      0      0
	ram6       0      0       0       0      0      0       0       0      0      0
	ram7       0      0       0       0      0      0       0       0      0      0
	ram8       0      0       0       0      0      0       0       0      0      0
	ram9       0      0       0       0      0      0       0       0      0      0
	ram10      0      0       0       0      0      0       0       0      0      0
	ram11      0      0       0       0      0      0       0       0      0      0
	ram12      0      0       0       0      0      0       0       0      0      0
	ram13      0      0       0       0      0      0       0       0      0      0
	ram14      0      0       0       0      0      0       0       0      0      0
	ram15      0      0       0       0      0      0       0       0      0      0
	loop0      0      0       0       0      0      0       0       0      0      0
	loop1      0      0       0       0      0      0       0       0      0      0
	loop2      0      0       0       0      0      0       0       0      0      0
	loop3      0      0       0       0      0      0       0       0      0      0
	loop4      0      0       0       0      0      0       0       0      0      0
	loop5      0      0       0       0      0      0       0       0      0      0
	loop6      0      0       0       0      0      0       0       0      0      0
	loop7      0      0       0       0      0      0       0       0      0      0
	sda    10521   6000  844022  801360 491454 135255 7228026 4453992      0   2951
	sr0        0      0       0       0      0      0       0       0      0      0
	dm-0   15559      0  837986 1088892 543656      0 7228016 5700568      0   2951
	dm-1     224      0    1792    7632      0      0       0       0      0      5
**FIELD DESCRIPTION FOR DISK MODE**


- Reads
	- total: Total reads completed successfully
	- merged: grouped reads (resulting in one I/O)
	- sectors: Sectors read successfully
	- ms: milliseconds spent reading

- Writes
	- total: Total writes completed successfully
	- merged: grouped writes (resulting in one I/O)
	- sectors: Sectors written successfully
	- ms: milliseconds spent writing

- IO
	- cur: I/O in progress
	- s: seconds spent for I/O

**vmstat -D**

           28 disks
            3 partitions
        26304 total reads
         6000 merged reads
      1683800 read sectors
      1897884 milli reading
      1035218 writes
       135271 merged writes
     14457066 written sectors
     10154976 milli writing
            0 inprogress IO
         5907 milli spent IO


