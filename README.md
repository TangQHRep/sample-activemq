# Spring-activeMQ
  在业务逻辑的异步处理，系统解耦，分布式通信以及控制高并发的场景下，消息队列有着广泛的应用。本项目基于Spring这一平台，整合流行的开源消息队列中间件ActiveMQ,实现一个向ActiveMQ添加和读取消息的功能。并比较了两种模式：生产者-消费者模式和发布-订阅模式的区别。
包含的特性如下：  
  
1.开启activeMQ，访问http://localhost:8080/demo 

2 在项目中，我们为消息的生产者和发布者分别注册了两个消费者和订阅者，当有消息到达activeMQ时，消费者和订阅者会自动获取对应的消息，其中两个消费者会轮流消费消息，而两个订阅者会同时订阅所有消息；

 
3.填入要发送的消息，点击生产消息可以向消息队列添加一条消息，我们可以试着添加了四条消息，并观察控制台结果，可以发现每个消息只被某一个消费者接收； 

 
4.重复以上操作发布四条消息，可以看到订阅者的输出结果，表明每个发布的消息可以被两个订阅者全部接收；
 
   
5.以上结果表明，向队列生产的每条消息，只能被某一个消费者读取，而发布的消息，可以被每个订阅者重复读取，这是两种模式最大的区别，实际应用中要根据情况来选择。


# ActiveMQ集群搭建


- activeMQ集群服务器个数为单数，一主多从，主服务器选举方式依赖zookeeper,因此zookeeper集群若不可用，则activeMQ集群不可用。客户端请求与主服务器通信，从服务器不工作，但能同步主服务器的数据。主服务器宕机，自动选举一台从服务器为主服务器。集群要求至少一半以上服务器不能宕机。
- 一个activeMQ集群中同一时刻只有一台主服务器在工作，为避免单点故障，可以建立多个activeMQ集群，然后配置将他们桥接起来，所有集群内的队列、生产者消费者都可以共享，以达到负载均衡，横向扩展集群性能的目的。
### 搭建第一个activeMQ集群（activeMQ版本号5.14.5）
找一台服务器，把activeMQ的安装文件夹拷贝两次，分别进行配置：
### 修改conf/jetty.xml
```
<bean id="jettyPort" class="org.apache.activemq.web.WebConsolePort" init-method="start">
<!-- the default port number for the web console -->
<property name="host" value="0.0.0.0"/>
<property name="port" value="8361"/>
</bean>
```
这里配置web控制台端口为8361，另外两台分别改为8362、8363。
### 修改conf/activemq.xml持久化适配器
```
<persistenceAdapter>
<!-- kahaDB directory="${activemq.data}/kahadb"/ -->
<replicatedLevelDB directory="${activemq.data}/leveldb" replicas="3" bind="tcp://0.0.0.0:63631" zkAddress="192.168.1.81:2181,192.168.1.82:2182,192.168.1.83:2183" hostname="edu-mq-01" zkPath="/activemq/leveldb-stores"/>
</persistenceAdapter>
```
```
<persistenceAdapter>
<!-- kahaDB directory="${activemq.data}/kahadb"/ -->
<replicatedLevelDB directory="${activemq.data}/leveldb" replicas="3" bind="tcp://0.0.0.0:63632"
zkAddress="192.168.1.81:2181,192.168.1.82:2182,192.168.1.83:2183" hostname="edu-mq-01" zkPath="/activemq/leveldb-stores"/>
</persistenceAdapter>
```
```
<persistenceAdapter>
<!-- kahaDB directory="${activemq.data}/kahadb"/ -->
<replicatedLevelDB directory="${activemq.data}/leveldb" replicas="3" bind="tcp://0.0.0.0:63633"
zkAddress="192.168.1.81:2181,192.168.1.82:2182,192.168.1.83:2183" hostname="edu-mq-01" zkPath="/activemq/leveldb-stores"/>
</persistenceAdapter>
```
zkAddress填zookeeper地址，ZkPath指集群选举信息在zookeeper的存放路径。
### 修改各节点的消息端口
```
<transportConnectors>
<!-- DOS protection, limit concurrent connections to 1000 and frame size to 100MB -->
<transportConnector name="openwire" uri="tcp://0.0.0.0:53531?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="amqp" uri="amqp://0.0.0.0:5662?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="stomp" uri="stomp://0.0.0.0:61613?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="mqtt" uri="mqtt://0.0.0.0:1883?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="ws" uri="ws://0.0.0.0:61614?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
</transportConnectors>
```
```
<transportConnectors>
<!-- DOS protection, limit concurrent connections to 1000 and frame size to 100MB -->
<transportConnector name="openwire" uri="tcp://0.0.0.0:53532?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="amqp" uri="amqp://0.0.0.0:5663?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="stomp" uri="stomp://0.0.0.0:61614?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="mqtt" uri="mqtt://0.0.0.0:1884?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="ws" uri="ws://0.0.0.0:61615?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
</transportConnectors>
```
```
<transportConnectors>
<!-- DOS protection, limit concurrent connections to 1000 and frame size to 100MB -->
<transportConnector name="openwire" uri="tcp://0.0.0.0:53533?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="amqp" uri="amqp://0.0.0.0:5664?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="stomp" uri="stomp://0.0.0.0:61615?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="mqtt" uri="mqtt://0.0.0.0:1885?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
<transportConnector name="ws" uri="ws://0.0.0.0:61616?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
</transportConnectors>
```
注意端口号不要冲突。
依次启动节点，观察zookeeper内数据的值，可以判断当前主节点是哪一台服务器。可以关闭部分服务器来测试集群的高可用性。
### java连接activeMQ
```
failover:(tcp://192.168.252.128:53531,tcp://192.168.252.128:53532,tcp://192.168.252.128:53533)?randomize=false
```
### 集群的扩展
由于一个activeMQ集群只有一个主服务器在工作，对消息的吞吐量是有限的。要想进行扩展，则需要把多个集群桥接起来，这样连接到任意一个集群，即可与整个集群的队列，生产者，消费者之间进行通信。在activemq.xml新增：
```
<networkConnectors>
<networkConnector
uri="static:(tcp://192.168.1.101:53531,tcp://192.168.1.101:53532,tcp://192.168.1.101:53533)"
duplex="false"/>
</networkConnectors>
```
以上配置即可完成到tcp://192.168.1.101:53531,tcp://192.168.1.101:53532,tcp://192.168.1.101:53533集群的链接。注意要相互配。
