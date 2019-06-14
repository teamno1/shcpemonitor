package cn.com.afcat.shcpemonitor.channel.socket.server;

import cn.com.afcat.shcpemonitor.channel.MessageInfo;
import cn.com.afcat.shcpemonitor.channel.socket.TcpComm;
import cn.com.afcat.shcpemonitor.channel.socket.TcpPackage;
import cn.com.afcat.shcpemonitor.common.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;


public class ServerHandler implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);
	
	private Socket	socket	= null;
	private int timeout=30;
	private String charset="GBK";
    private TcpComm comm=null;
    static final public String XML_HEAD = "<?xml version='1.0' encoding='UTF-8'?>";

	public ServerHandler(Socket socket, int timeout, String charset)
	{
		this.socket = socket;
		this.timeout=timeout;
		this.comm=new TcpComm(socket);
		this.charset=charset;
	}
	
	public ServerHandler(Socket socket, String charset)
	{
		this.socket = socket;
		this.comm = new TcpComm(socket);
		this.charset=charset;
	}
	
	public TcpComm getComm() {
		return comm;
	}
	
	public String getCharset() {
		return charset;
	}

	/**
	 * 通道逻辑处理
	 */
	public void run()
	{
		MessageInfo request = null; //接收请求报文信息
		//解析请求报文发送票交所MQ
		String answerData = null;
		try {
			request = receivePreServer();//接收请求报文信息
			logger.info(Thread.currentThread().getId()+"receive draft from preServer:" + request);
			//写发送票交所报文文件
			String filedir=request.getCreateDate()+File.separator+"down"+File.separator;
			String fileName=filedir+request.getMsgId();
	    	FileUtil.doWrite(filedir,fileName,request.toString());
	    	//解析报文路由
	    	/*Context context=ContextFactory.getContext();
	    	ContextUtil.setRequestData(context, request.toString());
	    	Event event=Event.createEvent("receiveDraft", context);
	    	EventHub hub=AppContext.getEventHub();
	    	event=hub.sendReceive(event);*/
	    	/*MsgHandler handler=AppContext.getMsgHandler();
	    	handler.process(request.toString());*/
	    	answerData = "0000";	
	    	
	    	
		} catch (Exception e) {
			e.printStackTrace();
			answerData = "0001";
		} finally{
			logger.info(Thread.currentThread().getId()+"send to preServer draft:" + answerData);
			sendPreServer(answerData);
			//关闭连接
			comm.close();
		}

	}
	
	
		
	/**
	 * 接收前置系统报文
	 * 方法中的一些数字说明详见： 票交所报文标准里的报文头格式说明（概述分册）
	 * @return
	 * @throws Exception 
	 * @throws IOException
	 */
	private MessageInfo receivePreServer() throws Exception {
		TcpPackage contentPack = null;
		MessageInfo msg = new MessageInfo();

		comm.setTimeOut(this.timeout);
		/*---------- 191位长度对应 票交所报文标准里的 报文头长度。----*/
		TcpPackage headPack = comm.recvPack(191);
		// 接收报文失败、即报文非法。不给应答 直接关闭socket
		if (headPack.getStatus() == TcpPackage.STATUS_TCP_TIME_OUT
				|| headPack.getStatus() == TcpPackage.STATUS_TCP_FAIL) {
			throw new Exception("接收票据系统报文头错误或者超时");
		}
		String head=new String(headPack.getContent(), charset);
		msg.setMsgHead(head);
		msg.setMsgId(head.substring(64, 84));
		msg.setCreateDate(head.substring(30,38));
		// 获取报文体内容
		contentPack = comm.recvPack(Integer.parseInt(head.substring(107, 115)));
		if (contentPack.getStatus() == TcpPackage.STATUS_TCP_TIME_OUT
				|| contentPack.getStatus() == TcpPackage.STATUS_TCP_FAIL) {
			throw new Exception("接收票据系统报文头错误或者超时");
		}
		msg.setMsgBody(new String(contentPack.getContent(), charset));

		return msg;
	}

	
	/**
	 * 返回票据系统应答报文
	 */
	private void sendPreServer(String xml) {

		try {
			if (xml != null) {
				byte[] content = xml.getBytes(charset);
				if (comm.sendMsg(content) < 0) {
					logger.error("send failed");
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}
		return;
	}
}
