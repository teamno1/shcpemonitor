package cn.com.afcat.shcpemonitor.channel.amq;

import cn.com.afcat.shcpemonitor.channel.IMsgSendClient;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.core.JmsTemplate;

/**
 * 消息发送器
 * @author litao
 */
public class AmqMessageSender implements IMsgSendClient{
	private JmsTemplate jmsTemplate;
	private ActiveMQQueue destination;
	
	
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}



	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}



	public ActiveMQQueue getDestination() {
		return destination;
	}



	public void setDestination(ActiveMQQueue destination) {
		this.destination = destination;
	}



	public String sendMessage(String msg)throws Exception{
		getJmsTemplate().convertAndSend(this.getDestination(), msg.getBytes("UTF-8"));
		return "0000";

			
	}
}
