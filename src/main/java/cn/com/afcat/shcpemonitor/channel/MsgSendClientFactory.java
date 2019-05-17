package cn.com.afcat.shcpemonitor.channel;

import cn.com.afcat.shcpemonitor.common.config.Global;
import cn.com.afcat.shcpemonitor.common.utils.ApplicationContextHolder;

/**
 * 消息发送客户端工厂
 * @author litao
 */
public class MsgSendClientFactory {
    public static IMsgSendClient getSendClient(){
        String clientType= Global.getConfig("clientType");
        if("activemq".equals(clientType)|| 1==1){
            return (IMsgSendClient) ApplicationContextHolder.getBean("amqSender");
        }else{
            return (IMsgSendClient) ApplicationContextHolder.getBean("socket");
        }
    }

}
