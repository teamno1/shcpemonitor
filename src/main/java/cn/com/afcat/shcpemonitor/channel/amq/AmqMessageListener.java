package cn.com.afcat.shcpemonitor.channel.amq;

import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.*;
import java.io.UnsupportedEncodingException;

/**
 * 消息监听器
 * @author litao
 */
public class AmqMessageListener implements SessionAwareMessageListener {

	public void onMessage(Message msg, Session session) throws JMSException {
		String msgContent = "";
		if(msg instanceof BytesMessage) {
			BytesMessage bm = (BytesMessage)msg;
    		int length = Long.valueOf(bm.getBodyLength()).intValue();
    		byte[] content = new byte[length];
    		bm.readBytes(content,length);
			//正常报文处理
    		try {
				msgContent = new String(content, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			//非规范报文,直接存储
			if(msg instanceof TextMessage) {
				msgContent = ((TextMessage)msg).getText();
			} else {
			}
		}
		if(!"".equals(msgContent)) {
			//new MQRecvMsgProcessThread(msgContent).start();
		}
	}

}
