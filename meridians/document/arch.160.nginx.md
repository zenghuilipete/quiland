1.Download
1.1 Download nginx source
sudo wget http://nginx.org/download/nginx-1.3.11.tar.gz -P /opt
sudo axel -n 10 -a -S5 http://nginx.org/download/nginx-1.3.11.tar.gz -o /opt

1.2. Download pcre(Perl Compatible Regular Expressions/Perl兼容正则表达式库)
sudo wget ftp://ftp.csx.cam.ac.uk/pub/software/programming/pcre/pcre-8.21.tar.bz2 -P /opt
sudo axel -n 10 -a -S5 ftp://ftp.csx.cam.ac.uk/pub/software/programming/pcre/pcre-8.21.tar.bz2 -o /opt

1.3. Download nginx-http-concat
sudo wget https://github.com/taobao/nginx-http-concat/archive/master.zip -P /opt

1.4. Download zlib
sudo wget https://github.com/feuyeux/meridians/raw/master/3rd-zlib-1.2.7.tar.bz2 -P /opt

2.Install
2.1 Unzip concat module
sudo apt-get install zip
sudo unzip -lv /opt/master.zip
sudo unzip /opt/master.zip -d /opt/
ls /opt/nginx-http-concat-master/
configngx_http_concat_module.cREADME.md

2.2. Install pcre
sudo tar xjpf /opt/pcre-8.21.tar.bz2 -C /opt
sudo chown -R eric:eric /opt/pcre-8.21
sudo apt-get install build-essential
sudo ./configure --prefix=/usr/local/pcre-8.21 --libdir=/usr/local/lib/pcre --includedir=/usr/local/include/pcre
sudo make && sudo make install

2.3. Install zlib
sudo tar xjpf /opt/zlib-1.2.7.tar.bz2 -C /opt
sudo ./configure && sudo make && sudo make install

2.4. Install nginx with concat module
sudo tar xvzf /opt/nginx-1.3.11.tar.gz -C /opt
sudo chown -R eric:eric /opt/nginx-1.3.11/
./configure --prefix=/usr/local/nginx --user=eric --group=eric --with-pcre=/opt/pcre-8.21 --with-zlib=/opt/zlib-1.2.7 --add-module=/opt/nginx-http-concat-master
sudo make && sudo make install

3. Test and Configure
/usr/local/nginx/sbin/nginx -V
nginx version: nginx/1.3.11
built by gcc 4.7.2 (Ubuntu/Linaro 4.7.2-2ubuntu1)
configure arguments: --prefix=/usr/local/nginx --user=eric --group=eric --with-pcre=/opt/pcre-8.21 --with-zlib=/opt/zlib-1.2.7 --add-module=/opt/nginx-http-concat-master

sudo nano /usr/local/nginx/conf/nginx.conf [folder: nginx-http-concat]
sudo chown -R eric:eric ~/nginx/
sudo /usr/local/nginx/sbin/nginx
curl http://localhost
http://192.168.0.160/

4. Log
tail /var/log/nginx/error.log
sudo /usr/local/nginx/sbin/nginx -s reload