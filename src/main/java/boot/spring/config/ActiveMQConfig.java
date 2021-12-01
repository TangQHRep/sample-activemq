package boot.spring.config;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;


/**
 * @ClassName： ActiveMQConfig.java
 * @author： Tangqh
 * @version： 1.0.0
 * @createTime： 2021年12月01日 10:01:00
 * @功能描述： 内置activemq配置
 */
@Configuration
public class ActiveMQConfig {

    private static final Logger log = LoggerFactory.getLogger(ActiveMQConfig.class);


    @Value("${spring.activemq.broker.persistent:false}")
    public Boolean brokerPersistent;

    @Value("${spring.activemq.broker.use-jmx:true}")
    public Boolean brokerUseJmx;

    @Value("${spring.activemq.broker.name}")
    public String brokerName;


    public ActiveMQConfig() {
    }

    @Bean
    @ConditionalOnProperty(
            name = {"spring.activemq.in-memory"},
            havingValue = "true"
    )
    public BrokerService getBroker(ActiveMQProperties activeMQProperties) {
        BrokerService broker = new BrokerService();
        broker.setBrokerName(brokerName);
        broker.setUseJmx(brokerUseJmx);
        broker.setPersistent(brokerPersistent);
        try {
            if (!StringUtils.isEmpty(activeMQProperties.getBrokerUrl())) {
                broker.addConnector(activeMQProperties.getBrokerUrl());
            }

            broker.start();
            return broker;
        } catch (Exception var4) {
            var4.printStackTrace();
            log.error(var4.getMessage());
            throw new RuntimeException("------内置ActiveMQ启动失败！-----------------------------");
        }
    }
    


}
