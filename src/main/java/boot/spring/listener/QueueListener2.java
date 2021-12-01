package boot.spring.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import boot.spring.po.Mail;

/**
 * @ClassName： QueueListener1.java
 * @author： Tangqh
 * @version： 1.0.0
 * @createTime： 2021年12月01日 10:13:19
 * @功能描述： 队列消费者
 */
@Component
public class QueueListener2 {
	
	@JmsListener(destination = "myqueue", concurrency = "2-10")
	public void displayMail(Mail mail) {
		System.out.println("listen2从ActiveMQ队列myqueue中取出一条消息：");
		System.out.println(mail.toString());
	}
}
