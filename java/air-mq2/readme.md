## GlassFish ##
### start ###
- export PATH=$PATH:/home/erichan/glassfish4/glassfish/bin
- PATH=%PATH%;D:\-aquarius\glassfish4\glassfish\bin
- 
`(Optional)asadmin create-domain`

`asadmin start-domain --verbose`

### setup resource ###
`cd D:\-aries\github\quiland\java\air-mq2\src\main\setup`
`asadmin add-resources glassfish-resources.xml`

	Administered object jms/Air-Queue created.
	Administered object jms/Air-Topic created.
	Command add-resources executed successfully.

`asadmin list-jms-resources`

	jms/Air-Queue
	jms/Air-Topic
	jms/__defaultConnectionFactory
	Command list-jms-resources executed successfully.

### admin-object-resource ###

|Attribute |Default |Description |
|jndi-name |none    |Specifies the JNDI name for the resource.
|res-type|none|Specifies the fully qualified type of the resource.
|res-adapter|none   |Specifies the name of the inbound resource adapter.
|object-type|user   |(optional) Defines the type of the resource. 
Allowed values are:
system-all - A system resource for all server instances and the domain application server.
system-admin - A system resource only for the domain application server.
system-instance - A system resource for all server instances only.
user - A user resource.|
|enabled |true     |(optional) Determines whether this resource is enabled at runtime.

default in: D:\-aquarius\glassfish4\glassfish\domains\domain1\config\domain.xml

    <connector-connection-pool max-pool-size="250" steady-pool-size="1" name="jms/__defaultConnectionFactory-Connection-Pool" resource-adapter-name="jmsra" connection-definition-name="javax.jms.ConnectionFactory"></connector-connection-pool>
    <connector-resource pool-name="jms/__defaultConnectionFactory-Connection-Pool" jndi-name="jms/__defaultConnectionFactory" object-type="system-all-req"></connector-resource>

## JMS2.0(JSR 343)##
#### 1.1 Produce Queue ####
`cd ~/glassfish4/docs/javaee-tutorial/examples/jms/simple/producer`
`appclient -client target/producer.jar queue 3`

	Dec 13, 2013 3:29:52 PM org.hibernate.validator.internal.util.Version <clinit>
	INFO: HV000001: Hibernate Validator 5.0.0.Final
	Dec 13, 2013 3:29:53 PM com.sun.messaging.jms.ra.ResourceAdapter start
	INFO: MQJMSRA_RA1101: GlassFish MQ JMS Resource Adapter: Version:  5.0  (Build 14-e) Compile:  April 12 2013 0104
	Dec 13, 2013 3:29:53 PM com.sun.messaging.jms.ra.ResourceAdapter start
	INFO: MQJMSRA_RA1101: GlassFish MQ JMS Resource Adapter starting: broker is REMOTE, connection mode is TCP
	Dec 13, 2013 3:29:53 PM com.sun.messaging.jms.ra.ResourceAdapter start
	INFO: MQJMSRA_RA1101: GlassFish MQ JMS Resource Adapter Started:REMOTE
	Destination type is queue
	Sending message: This is message 1 from producer
	Sending message: This is message 2 from producer
	Sending message: This is message 3 from producer
	Messages sent: 3

#### 1.2 Consume Queue ####
`cd ~/glassfish4/docs/javaee-tutorial/examples/jms/simple/synchconsumer`
`appclient -client target/synchconsumer.jar queue`

	Dec 13, 2013 3:31:39 PM org.hibernate.validator.internal.util.Version <clinit>
	INFO: HV000001: Hibernate Validator 5.0.0.Final
	Dec 13, 2013 3:31:40 PM com.sun.messaging.jms.ra.ResourceAdapter start
	INFO: MQJMSRA_RA1101: GlassFish MQ JMS Resource Adapter: Version:  5.0  (Build 14-e) Compile:  April 12 2013 0104
	Dec 13, 2013 3:31:40 PM com.sun.messaging.jms.ra.ResourceAdapter start
	INFO: MQJMSRA_RA1101: GlassFish MQ JMS Resource Adapter starting: broker is REMOTE, connection mode is TCP
	Dec 13, 2013 3:31:40 PM com.sun.messaging.jms.ra.ResourceAdapter start
	INFO: MQJMSRA_RA1101: GlassFish MQ JMS Resource Adapter Started:REMOTE
	Destination type is queue
	Reading message: This is message 1 from producer
	Reading message: This is message 2 from producer
	Reading message: This is message 3 from producer
	Messages received: 3

#### 2.1 Consume topic ####
`cd ~/glassfish4/docs/javaee-tutorial/examples/jms/simple/synchconsumer`
`appclient -client target/synchconsumer.jar topic`

	Dec 13, 2013 3:38:37 PM org.hibernate.validator.internal.util.Version <clinit>
	INFO: HV000001: Hibernate Validator 5.0.0.Final
	Dec 13, 2013 3:38:37 PM com.sun.messaging.jms.ra.ResourceAdapter start
	INFO: MQJMSRA_RA1101: GlassFish MQ JMS Resource Adapter: Version:  5.0  (Build 14-e) Compile:  April 12 2013 0104
	Dec 13, 2013 3:38:37 PM com.sun.messaging.jms.ra.ResourceAdapter start
	INFO: MQJMSRA_RA1101: GlassFish MQ JMS Resource Adapter starting: broker is REMOTE, connection mode is TCP
	Dec 13, 2013 3:38:37 PM com.sun.messaging.jms.ra.ResourceAdapter start
	INFO: MQJMSRA_RA1101: GlassFish MQ JMS Resource Adapter Started:REMOTE
	Destination type is topic
	Reading message: This is message 1 from producer
	Reading message: This is message 2 from producer
	Reading message: This is message 3 from producer
	Messages received: 3

#### 2.2 Produce topic ####
`~/glassfish4/docs/javaee-tutorial/examples/jms/simple/producer`
`appclient -client target/producer.jar topic 3`

	Dec 13, 2013 3:39:01 PM org.hibernate.validator.internal.util.Version <clinit>
	INFO: HV000001: Hibernate Validator 5.0.0.Final
	Dec 13, 2013 3:39:02 PM com.sun.messaging.jms.ra.ResourceAdapter start
	INFO: MQJMSRA_RA1101: GlassFish MQ JMS Resource Adapter: Version:  5.0  (Build 14-e) Compile:  April 12 2013 0104
	Dec 13, 2013 3:39:02 PM com.sun.messaging.jms.ra.ResourceAdapter start
	INFO: MQJMSRA_RA1101: GlassFish MQ JMS Resource Adapter starting: broker is REMOTE, connection mode is TCP
	Dec 13, 2013 3:39:02 PM com.sun.messaging.jms.ra.ResourceAdapter start
	INFO: MQJMSRA_RA1101: GlassFish MQ JMS Resource Adapter Started:REMOTE
	Destination type is topic
	Sending message: This is message 1 from producer
	Sending message: This is message 2 from producer
	Sending message: This is message 3 from producer
	Messages sent: 3

#### 3 async Consume topic ####
`cd ~/glassfish4/docs/javaee-tutorial/examples/jms/simple/asynchconsumer`
`appclient -client target/asynchconsumer.jar topic`

	Dec 13, 2013 3:40:45 PM org.hibernate.validator.internal.util.Version <clinit>
	INFO: HV000001: Hibernate Validator 5.0.0.Final
	Dec 13, 2013 3:40:46 PM com.sun.messaging.jms.ra.ResourceAdapter start
	INFO: MQJMSRA_RA1101: GlassFish MQ JMS Resource Adapter: Version:  5.0  (Build 14-e) Compile:  April 12 2013 0104
	Dec 13, 2013 3:40:46 PM com.sun.messaging.jms.ra.ResourceAdapter start
	INFO: MQJMSRA_RA1101: GlassFish MQ JMS Resource Adapter starting: broker is REMOTE, connection mode is TCP
	Dec 13, 2013 3:40:46 PM com.sun.messaging.jms.ra.ResourceAdapter start
	INFO: MQJMSRA_RA1101: GlassFish MQ JMS Resource Adapter Started:REMOTE
	Destination type is topic
	To end program, type Q or q, then <return>
	Reading message: This is message 1 from producer
	Reading message: This is message 2 from producer
	Reading message: This is message 3 from producer
	Message is not a TextMessage

`cd ~/glassfish4/docs/javaee-tutorial/examples/jms/simple/producer`
`appclient -client target/producer.jar topic 3`

	Dec 13, 2013 3:41:08 PM org.hibernate.validator.internal.util.Version <clinit>
	INFO: HV000001: Hibernate Validator 5.0.0.Final
	Dec 13, 2013 3:41:08 PM com.sun.messaging.jms.ra.ResourceAdapter start
	INFO: MQJMSRA_RA1101: GlassFish MQ JMS Resource Adapter: Version:  5.0  (Build 14-e) Compile:  April 12 2013 0104
	Dec 13, 2013 3:41:08 PM com.sun.messaging.jms.ra.ResourceAdapter start
	INFO: MQJMSRA_RA1101: GlassFish MQ JMS Resource Adapter starting: broker is REMOTE, connection mode is TCP
	Dec 13, 2013 3:41:08 PM com.sun.messaging.jms.ra.ResourceAdapter start
	INFO: MQJMSRA_RA1101: GlassFish MQ JMS Resource Adapter Started:REMOTE
	Destination type is topic
	Sending message: This is message 1 from producer
	Sending message: This is message 2 from producer
	Sending message: This is message 3 from producer
	Messages sent: 3

### 4 browse queue ###
`cd ~/glassfish4/docs/javaee-tutorial/examples/jms/simple/producer`
`appclient -client target/producer.jar queue`

`cd ~/glassfish4/docs/javaee-tutorial/examples/jms/simple/messagebrowser` `appclient -client target/messagebrowser.jar`

	Dec 13, 2013 3:43:33 PM org.hibernate.validator.internal.util.Version <clinit>
	INFO: HV000001: Hibernate Validator 5.0.0.Final
	Dec 13, 2013 3:43:33 PM com.sun.messaging.jms.ra.ResourceAdapter start
	INFO: MQJMSRA_RA1101: GlassFish MQ JMS Resource Adapter: Version:  5.0  (Build 14-e) Compile:  April 12 2013 0104
	Dec 13, 2013 3:43:33 PM com.sun.messaging.jms.ra.ResourceAdapter start
	INFO: MQJMSRA_RA1101: GlassFish MQ JMS Resource Adapter starting: broker is REMOTE, connection mode is TCP
	Dec 13, 2013 3:43:33 PM com.sun.messaging.jms.ra.ResourceAdapter start
	INFO: MQJMSRA_RA1101: GlassFish MQ JMS Resource Adapter Started:REMOTE
	
	Message: 
	Text:	This is message 1 from producer
	Class:			com.sun.messaging.jmq.jmsclient.TextMessageImpl
	getJMSMessageID():	ID:9-127.0.1.1(a0:31:a1:8b:e6:c1)-34995-1386920551779
	getJMSTimestamp():	1386920551779
	getJMSCorrelationID():	null
	JMSReplyTo:		null
	JMSDestination:		PhysicalQueue
	getJMSDeliveryMode():	NON PERSISTENT
	getJMSRedelivered():	false
	getJMSType():		null
	getJMSExpiration():	0
	getJMSDeliveryTime():	0
	getJMSPriority():	4
	Properties:		{JMSXDeliveryCount=0}
	
	Message: 
	Class:			com.sun.messaging.jmq.jmsclient.MessageImpl
	getJMSMessageID():	ID:10-127.0.1.1(a0:31:a1:8b:e6:c1)-34995-1386920551779
	getJMSTimestamp():	1386920551779
	getJMSCorrelationID():	null
	JMSReplyTo:		null
	JMSDestination:		PhysicalQueue
	getJMSDeliveryMode():	PERSISTENT
	getJMSRedelivered():	false
	getJMSType():		null
	getJMSExpiration():	0
	getJMSDeliveryTime():	0
	getJMSPriority():	4
	Properties:		{JMSXDeliveryCount=0}