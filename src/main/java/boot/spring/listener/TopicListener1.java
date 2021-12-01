package boot.spring.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import boot.spring.po.Mail;

/**
 * @ClassName： TopicListener1.java
 * @author： Tangqh
 * @version： 1.0.0
 * @createTime： 2021年12月01日 10:09:12
 * @功能描述： 主题消费者
 */
@Component
public class TopicListener1 {
	
	@JmsListener(destination = "mytopic", containerFactory="topicListenerFactory")
	public void displayTopic(Mail msg) {
		System.out.println("consumer1从ActiveMQ的Topic：mytopic中取出一条消息：");
		System.out.println(msg);
	}
}
