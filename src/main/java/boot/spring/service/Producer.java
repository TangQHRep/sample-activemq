package boot.spring.service;

import javax.jms.Destination;

import boot.spring.po.Mail;
import org.springframework.messaging.Message;

public interface Producer {

	public void sendMail(Destination des, Mail mail);

	void convertAndSendPointMessage(String destinationName, Object payload);

	void convertAndSendTopicMessage(String destinationName, Object payload);

	void sendPointMessage(String destinationName, Message<?> message);

	void sendTopicMessage(String destinationName, Message<?> message);

	void convertAndSendMessage(Destination destination, Object payload);

	void sendMessage(Destination destination, Message<?> message);

	<T> T convertAndSendPointMessageAndReceive(String destinationName, Object payload, Class<T> tClass);

	<T> T convertAndSendMessageAndReceive(Destination destination, Object payload, Class<T> tClass);

	Message<?> sendPointMessageAndReceive(String destinationName, Message<?> message);

	Message<?> sendMessageAndReceive(Destination destination, Message<?> message);
}
