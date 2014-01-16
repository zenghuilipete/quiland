## df ##
report file system disk space usage

	-a, --all
	      include dummy file systems
	-h, --human-readable
	      print sizes in human readable format (e.g., 1K 234M 2G)
	-l, --local
	      limit listing to local file systems
	-T, --print-type
	      print file system type

**df -ahT**

	Filesystem                       Type        Size  Used Avail Use% Mounted on
	/dev/mapper/CI--Jenkins--vg-root ext4         70G  4.1G   63G   7% /
	proc                             proc           0     0     0    - /proc
	sysfs                            sysfs          0     0     0    - /sys
	none                             tmpfs       4.0K     0  4.0K   0% /sys/fs/cgroup
	none                             fusectl        0     0     0    - /sys/fs/fuse/connections
	none                             debugfs        0     0     0    - /sys/kernel/debug
	none                             securityfs     0     0     0    - /sys/kernel/security
	udev                             devtmpfs    1.5G  4.0K  1.5G   1% /dev
	devpts                           devpts         0     0     0    - /dev/pts
	tmpfs                            tmpfs       300M  580K  300M   1% /run
	none                             tmpfs       5.0M     0  5.0M   0% /run/lock
	none                             tmpfs       1.5G     0  1.5G   0% /run/shm
	none                             tmpfs       100M     0  100M   0% /run/user
	none                             pstore         0     0     0    - /sys/fs/pstore
	/dev/sda1                        ext2        236M   34M  190M  15% /boot
	systemd                          cgroup         0     0     0    - /sys/fs/cgroup/systemd

