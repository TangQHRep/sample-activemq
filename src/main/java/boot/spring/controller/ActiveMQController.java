package boot.spring.controller;


import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import boot.spring.po.Mail;
import boot.spring.service.Producer;



@Controller
public class ActiveMQController {
	
	@Autowired
	Producer producer;

	@RequestMapping("demo")
	public String demo(){
		return "demo";
	}
	
	@RequestMapping(value="/produce",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public void produce(@ModelAttribute("mail") Mail mail) throws Exception{
		producer.convertAndSendPointMessage("myqueue", mail);
	}
	
	@RequestMapping(value="/topic",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public void topic(@ModelAttribute("mail")Mail mail) throws Exception{
		producer.convertAndSendPointMessage("mytopic", mail);
	}
	
}
