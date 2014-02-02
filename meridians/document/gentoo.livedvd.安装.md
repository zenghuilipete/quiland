主机启动的时候tab键加nox，启动后，开启ssh服务
sudo /etc/init.d/sshd start
ifconfig
设置root密码
sudo su -
passwd
(root)
PuTTY使用root/root访问主机

1.设置硬盘
fdisk /dev/sda
boot分区100M
	Command (m for help): n
	Partition type:
	   p   primary (0 primary, 0 extended, 4 free)
	   e   extended
	Select (default p): p
	Partition number (1-4, default 1):
	Using default value 1
	First sector (2048-41943039, default 2048):
	Using default value 2048
	Last sector, +sectors or +size{K,M,G} (2048-41943039, default 41943039): +100M
	Partition 1 of type Linux and of size 100 MiB is set

	Command (m for help): a
	Partition number (1-4): 1

交换分区1G
	Command (m for help): n
	Partition type:
	   p   primary (1 primary, 0 extended, 3 free)
	   e   extended
	Select (default p): p
	Partition number (1-4, default 2):
	Using default value 2
	First sector (206848-41943039, default 206848):
	Using default value 206848
	Last sector, +sectors or +size{K,M,G} (206848-41943039, default 41943039): +1G
	Partition 2 of type Linux and of size 1 GiB is set

	Command (m for help): t
	Partition number (1-4): 2
	Hex code (type L to list codes): 82
	Changed system type of partition 2 to 82 (Linux swap / Solaris)

root分区使用余下磁盘空间
Command (m for help): n
Partition type:
   p   primary (2 primary, 0 extended, 2 free)
   e   extended
Select (default p): p
Partition number (1-4, default 3):
Using default value 3
First sector (2304000-41943039, default 2304000):
Using default value 2304000
Last sector, +sectors or +size{K,M,G} (2304000-41943039, default 41943039):
Using default value 41943039
Partition 3 of type Linux and of size 18.9 GiB is set

查看分区结果
Command (m for help): p
   Device Boot      Start         End      Blocks   Id  System
/dev/sda1   *        2048      206847      102400   83  Linux
/dev/sda2          206848     2303999     1048576   82  Linux swap / Solaris
/dev/sda3         2304000    41943039    19819520   83  Linux

2.格式化分割区
mkfs.ext3 /dev/sda1
mkswap /dev/sda2
mkfs.ext4 /dev/sda3

3.挂载分割区
swapon /dev/sda2
mount /dev/sda3 /mnt/gentoo
mkdir /mnt/gentoo/boot
mount /dev/sda1 /mnt/gentoo/boot

4.复制Live DVD Gentoo文件
cp -apf /mnt/livecd/* /mnt/gentoo/

5.把环境切换到硬盘
chroot /mnt/gentoo /bin/bash
env-update && source /etc/profile

6.修改root密码
passwd
(root)

注解 /etc/conf.d/local.start 里面全部东西，或用下面的指令清空
cat /dev/null > /etc/conf.d/local.start
rc-update del autoconfig

7.设定时区
rm /etc/localtime
ln –s /usr/share/zoneinfo/Asia/Taipei /etc/localtime

8.修改/etc/fstab
nano /etc/fstab
/dev/sda1 /boot ext3 defaults 1 2
/dev/sda2 none swap sw 0 0
/dev/sda3 / ext4 defaults 0 1

9.复制 kernel 到 /boot区
uname -a
Linux Gentoo-20121221 3.6.8-gentoo-r1 #1 SMP Thu Dec 20 04:20:10 UTC 2012 x86_64 GNU/Linux

cp /usr/src/linux/System.map /boot/System.map-genkernel-x86_64-3.6.8-gentoo-r1
cp /usr/src/linux/arch/x86_64/boot/bzImage /boot/kernel-genkernel-x86_64-3.6.8-gentoo-r1
genkernel ramdisk

10.编辑/boot/grub/grub.conf
nano /boot/grub/grub.conf
default 0
timeout 30
title Linux Gentoo-20121221 3.6.8-gentoo-r1
root (hd0,0)
kernel /boot/System.map-genkernel-x86_64-3.6.8-gentoo-r1 root=/dev/rm0 real_root=/dev/sda3
initrd /boot/initramfs-genkernel-x86_64-3.6.8-gentoo-r1
退出环境，重开机，退出光碟