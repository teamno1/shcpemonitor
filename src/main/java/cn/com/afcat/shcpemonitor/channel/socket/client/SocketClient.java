package cn.com.afcat.shcpemonitor.channel.socket.client;


import cn.com.afcat.shcpemonitor.channel.IMsgSendClient;
import cn.com.afcat.shcpemonitor.channel.socket.TcpComm;
import cn.com.afcat.shcpemonitor.channel.socket.TcpPackage;
import cn.com.afcat.shcpemonitor.common.config.Global;

import java.io.UnsupportedEncodingException;

public class SocketClient implements IMsgSendClient {

	/**
	 * 发送报文
	 * 返回0000即为成功，其他码值为异常
	 * @return
	 */
	public  String sendMessage(String draft){
		TcpComm comm = new TcpComm();
		String result = "0000";
		int ret = 0;

		ret = comm.call(Global.getConfig("remote_ip"), Global.getIntegerConfig("remote_port"));
		// 连接远程机器，如果连接失败返回连接失败错误码和错误信息
		if (ret < 0) {
			result = "E";
			return result;
		}

		// 发送请求报文
		ret = send(comm, draft);
		if (ret < 0) {
			result = "E";
			return result;
		}
		// 接收应答报文
		result = receiveResp(comm);
		if (result == null) {
			result = "E";
			return result;
		} else {
			return result;
		}
	}
	/**
	 * 发送请求
	 * 
	 * @param comm
	 * @return
	 */
	private int send(TcpComm comm,String request) {
		try{
			byte[] content=request.getBytes(Global.getConfig("server.charset","UTF-8"));
			if(comm.sendMsg(content) < 0){
				return -1;
			}
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	/**
	 * 接收应答报文
	 * 
	 * @param comm
	 * @return
	 */
	private String receiveResp(TcpComm comm) {
		String result = null;
		try {
			comm.setTimeOut(Global.getIntegerConfig("server.timeout",6000));
			TcpPackage headPack = comm.recvPack(4);
			// 接收报文失败、即报文非法。不给应答 直接关闭socket
			if (headPack.getStatus() == TcpPackage.STATUS_TCP_TIME_OUT
					|| headPack.getStatus() == TcpPackage.STATUS_TCP_FAIL) {
				return null;
			}
			//转换成目标字符集,并转换内部数据集
			result = new String(headPack.getContent(),Global.getConfig("server.charset","UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}finally{
			comm.close();
		}
		return result;
	}
	
}
