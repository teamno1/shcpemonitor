package cn.com.afcat.shcpemonitor.channel.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;


public class DiscardPolicy implements RejectedExecutionHandler {

	private static final Logger log = LoggerFactory.getLogger(DiscardPolicy.class);
	
	/**
     * 系统核心服务忙情况下，先接收完毕报文给忙提示信息，不纳入线程池处理
     */
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		log.error("service is busing now");
		
		ServerHandler task=(ServerHandler)r;
		String answerData = "0001";
		log.info("send to preServer draft:" + answerData);
		
		byte[] content;
		try {
			content = answerData.getBytes(task.getCharset());
			if (task.getComm().sendMsg(content) < 0) {
				log.error("send to preServer draft fail");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally{
			
			if (task.getComm() != null){
				task.getComm().close();
			}
		}
	}
}
