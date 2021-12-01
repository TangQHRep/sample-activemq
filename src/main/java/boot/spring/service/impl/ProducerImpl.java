package boot.spring.service.impl;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import boot.spring.po.Mail;
import boot.spring.service.Producer;

@Service("producer")
public class ProducerImpl implements Producer{
	
	@Autowired
    public JmsMessagingTemplate jmsTemplate;
	

	@Override
	public void sendMail(Destination des, Mail mail) {
		jmsTemplate.convertAndSend(des, mail);
	}

	@Override
	public void convertAndSendPointMessage(String destinationName, Object payload) {
		ActiveMQQueue activeMQQueue = new ActiveMQQueue(destinationName);
		this.jmsTemplate.convertAndSend(activeMQQueue, payload);
	}

	@Override
	public void convertAndSendTopicMessage(String destinationName, Object payload) {
		ActiveMQTopic activeMQTopic = new ActiveMQTopic(destinationName);
		this.jmsTemplate.convertAndSend(activeMQTopic, payload);
	}

	@Override
	public void sendPointMessage(String destinationName, Message<?> message) {
		ActiveMQQueue activeMQQueue = new ActiveMQQueue(destinationName);
		this.jmsTemplate.convertAndSend(activeMQQueue, message);
	}

	@Override
	public void sendTopicMessage(String destinationName, Message<?> message) {
		ActiveMQTopic activeMQTopic = new ActiveMQTopic(destinationName);
		this.jmsTemplate.convertAndSend(activeMQTopic, message);
	}

	@Override
	public void convertAndSendMessage(Destination destination, Object payload) {
		this.jmsTemplate.convertAndSend(destination, payload);
	}

	@Override
	public void sendMessage(Destination destination, Message<?> message) {
		this.jmsTemplate.send(destination, message);
	}

	@Override
	public <T> T convertAndSendPointMessageAndReceive(String destinationName, Object payload, Class<T> tClass) {
		ActiveMQQueue activeMQQueue = new ActiveMQQueue(destinationName);
		return this.jmsTemplate.convertSendAndReceive(activeMQQueue, payload, tClass);
	}

	@Override
	public <T> T convertAndSendMessageAndReceive(Destination destination, Object payload, Class<T> tClass) {
		return this.jmsTemplate.convertSendAndReceive(destination, payload, tClass);
	}

	@Override
	public Message<?> sendPointMessageAndReceive(String destinationName, Message<?> message) {
		ActiveMQQueue activeMQQueue = new ActiveMQQueue(destinationName);
		return this.jmsTemplate.sendAndReceive(activeMQQueue, message);
	}

	@Override
	public Message<?> sendMessageAndReceive(Destination destination, Message<?> message) {
		return this.jmsTemplate.sendAndReceive(destination, message);
	}

	public JmsMessagingTemplate getJmsTemplate() {
		return this.jmsTemplate;
	}
	

}
