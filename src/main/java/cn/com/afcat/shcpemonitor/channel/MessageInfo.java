package cn.com.afcat.shcpemonitor.channel;

public class MessageInfo {
	
	private String msgHead;
	private String msgBody;
	private String msgId;
	private String createDate;
	
	public String getMsgHead() {
		return msgHead;
	}
	public void setMsgHead(String msgHead) {
		this.msgHead = msgHead;
	}
	public String getMsgBody() {
		return msgBody;
	}
	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String toString() {
		return msgHead+msgBody;
	}
	

}
