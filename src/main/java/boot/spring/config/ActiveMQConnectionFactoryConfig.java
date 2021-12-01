package boot.spring.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQConnectionFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName： ActiveMQConnectionFactoryConfig.java
 * @author： Tangqh
 * @version： 1.0.0
 * @createTime： 2021年12月01日 10:06:00
 * @功能描述： 自定义activemq配置
 */
@Component
public class ActiveMQConnectionFactoryConfig implements ActiveMQConnectionFactoryCustomizer {

    private static final Logger log = LoggerFactory.getLogger(ActiveMQConnectionFactoryConfig.class);

    public ActiveMQConnectionFactoryConfig() {
    }

    @Override
    public void customize(ActiveMQConnectionFactory factory) {
        log.info("对ActiveMQConnectionFactory进行自定义配置--------------------");
    }


}
