package cn.com.afcat.shcpemonitor.channel.amq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

/**
 * ACTIVEMQ 配置
 * @author litao
 */
@EnableJms
@Configuration
public class JmsConfig {

    @Value("${activemq.url}")
    String url;
    @Value("${activemq.username}")
    String userName;
    @Value("${activemq.password}")
    String password;
    @Value("${activemq.receiveQName}")
    String receiveQName;
    @Value("${activemq.sendQName}")
    String sendQName;
    @Value("${activemq.listenerSwitch}")
    String listenerSwitch;

    @Bean
    public ActiveMQConnectionFactory amqConnectionFactory (){
        ActiveMQConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory(
                        userName,
                        password,
                        url);
        return activeMQConnectionFactory;
    }

    @Bean
    public PooledConnectionFactory amqConnectionPoolFactory(ActiveMQConnectionFactory amqConnectionFactory ){
        PooledConnectionFactory amqConnectionPoolFactory=new PooledConnectionFactory();
        amqConnectionPoolFactory.setConnectionFactory(amqConnectionFactory);
        amqConnectionPoolFactory.setMaxConnections(10);
        return amqConnectionPoolFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(PooledConnectionFactory amqConnectionPoolFactory){
        JmsTemplate jmsTemplate=new JmsTemplate();
        jmsTemplate.setConnectionFactory(amqConnectionPoolFactory);
        //jmsTemplate.setSessionTransacted(true);
        return jmsTemplate;
    }

    @Bean
    public ActiveMQQueue amqReceiveQueue(){
        ActiveMQQueue queue=new ActiveMQQueue();
        queue.setPhysicalName(receiveQName);
        return queue;
    }

    @Bean
    public ActiveMQQueue amqSendQueue(){
        ActiveMQQueue queue=new ActiveMQQueue();
        queue.setPhysicalName(sendQName);
        return queue;
    }

    @Bean
    public AmqMessageListener amqListener(){
        return new AmqMessageListener();

    }

    @Bean
    public AmqMessageSender amqSender(JmsTemplate jmsTemplate,ActiveMQQueue amqSendQueue){
        AmqMessageSender sender=new AmqMessageSender();
        sender.setJmsTemplate(jmsTemplate);
        sender.setDestination(amqSendQueue);
        return sender;
    }

    @Bean
    public AmqMessageListenerContainer amqListenerContainer(ActiveMQConnectionFactory amqConnectionFactory,
                                                            ActiveMQQueue amqReceiveQueue,AmqMessageListener amqListener){
        AmqMessageListenerContainer container=new AmqMessageListenerContainer();
        container.setListenSwitch(listenerSwitch);
        container.setConnectionFactory(amqConnectionFactory);
        container.setDestination(amqReceiveQueue);
        container.setMessageListener(amqListener);
        //container.setSessionTransacted(true);
        container.setMaxMessagesPerTask(1);
        return container;

    }
}
