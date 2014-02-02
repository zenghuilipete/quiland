/opt/nexus-2.3.0-04/bin/jsw/linux-x86-64/nexus start
----
Download
	sudo axel http://download.sonatype.com/nexus/oss/nexus-2.3.0-04-bundle.tar.gz

Install
	sudo tar -xzf nexus-2.3.0-04-bundle.tar.gz
	sudo chown -R eric:eric /opt/nexus-2.3.0-04
	sudo chown -R eric:eric /opt/sonatype-work

Launch
	/opt/nexus-2.3.0-04/bin/nexus start
	/opt/nexus-2.3.0-04/bin/jsw/linux-x86-64/nexus start

Configure
	nano .bashrc
		export NEXUS_HOME=/opt/nexus-2.3.0-04
	source .bashrc

Visit
	http://192.168.0.130:8081/nexus/index.html
	default user/password: admin/admin123

Running as a Service
	sudo cp $NEXUS_HOME/bin/nexus /etc/init.d/nexus
	sudo chmod 755 /etc/init.d/nexus
	sudo nano /etc/init.d/nexus
		modify:
		NEXUS_HOME="/opt/nexus-2.3.0-04"

	cd /etc/init.d

sudo update-rc.d -f nexus remove
sudo update-rc.d nexus defaults

update-rc.d: warning: /etc/init.d/nexus missing LSB information
update-rc.d: see <http://wiki.debian.org/LSBInitScripts>

	service nexus start
		Starting Nexus OSS...
		Started Nexus OSS.
	tail -f $NEXUS_HOME/logs/wrapper.log
		jvm 1    | 2013-02-12 07:33:39 INFO  [y-main-thread-1] - org.sonatype.nexus.proxy.registry.DefaultRepositoryRegistry - Added repository "Snapshots" [id=snapshots][contentClass=Maven2][mainFacet=org.sonatype.nexus.proxy.maven.MavenHostedRepository]
		jvm 1    | 2013-02-12 07:33:39 INFO  [y-main-thread-1] - org.sonatype.nexus.proxy.registry.DefaultRepositoryRegistry - Added repository "3rd party" [id=thirdparty][contentClass=Maven2][mainFacet=org.sonatype.nexus.proxy.maven.MavenHostedRepository]
		jvm 1    | 2013-02-12 07:33:39 INFO  [y-main-thread-1] - org.sonatype.nexus.proxy.registry.DefaultRepositoryRegistry - Added repository "Central M1 shadow" [id=central-m1][contentClass=Maven1][mainFacet=org.sonatype.nexus.proxy.maven.MavenShadowRepository]
		jvm 1    | 2013-02-12 07:33:39 INFO  [y-main-thread-1] - org.sonatype.nexus.proxy.registry.DefaultRepositoryRegistry - Added repository "Public Repositories" [id=public][contentClass=Maven2][mainFacet=org.sonatype.nexus.proxy.repository.GroupRepository]
		jvm 1    | 2013-02-12 07:33:39 INFO  [y-main-thread-1] - org.sonatype.scheduling.DefaultScheduler - Initializing Scheduler...
		jvm 1    | 2013-02-12 07:33:39 INFO  [y-main-thread-1] - org.sonatype.scheduling.DefaultTaskConfigManager - 0 task(s) to load.
		jvm 1    | 2013-02-12 07:33:39 INFO  [y-main-thread-1] - org.sonatype.nexus.DefaultNexus - Nexus Work Directory : /opt/sonatype-work/nexus
		jvm 1    | 2013-02-12 07:33:39 INFO  [y-main-thread-1] - org.sonatype.nexus.DefaultNexus - Started Sonatype Nexus 2.3.0-04
		jvm 1    | 2013-02-12 07:33:39 INFO  [y-main-thread-1] - org.eclipse.jetty.server.handler.ContextHandler - started o.e.j.w.WebAppContext{/nexus,file:/opt/nexus-2.3.0-04/nexus/},/opt/nexus-2.3.0-04/nexus
		jvm 1    | 2013-02-12 07:33:41 INFO  [y-main-thread-1] - org.eclipse.jetty.server.AbstractConnector - Started SelectChannelConnector@0.0.0.0:8081

Advanced
nexus的工作目录
默认情况下，nexus的工作目录${user_home}/sonatype-work目录，在linux下如果是root用户就是：/root/sonatype-work
这方便了nexus通过war安装到servlet容器中，但不利于服务器的集中管理。为了管理方便，一般要将服务器的数据统一放置到/srv或指定目录下.
可以在tomcat的启动脚本中设置环境变量：export PLEXUS_NEXUS_WORK=/srv/nexus
可以通过java运行时的系统属性：-DPLEXUS_NEXUS_WORK=/srv/nexus-work
可以通过更改该web应用中的nexus配置文件plexus.properties：nexus-work=/srv/nexus-work
以上设置的优先次序是：plexus.properties>系统属性>环境变量>默认设置