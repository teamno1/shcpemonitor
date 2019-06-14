package cn.com.afcat.shcpemonitor.channel.socket;

public class TcpPackage {
	
	/**
	 * 报文内容
	 */
	private byte[] content;
	/**
	 * 报文长度
	 */
	private int contentLen;
	
	/**
	 * 通信状态
	 */
	private int status;
	
	/**
	 * 通讯超时
	 */
	public static final int STATUS_TCP_TIME_OUT = -1;
	/**
	 * 通讯正常
	 */
	public static final int STATUS_TCP_NORMAL = 0;
	
	/**
	 * 通讯失败
	 */
	public static final int STATUS_TCP_FAIL = -2;
	
	/**
	 * 构造函数
	 */
	public TcpPackage(){
		this.status=STATUS_TCP_NORMAL;
		this.contentLen=0;
		this.content=new byte[0];  //默认空串
	}
	
	/**
	 * 获取报文内容
	 * @return
	 */
	public byte[] getContent() {
		return content;
	}
    /**
     * 设置报文内容
     * @param content
     */
	public void setContent(byte[] content) {
		this.content = content;
	}
    /**
     * 获取报文长度
     * @return
     */
	public int getContentLen() {
		return contentLen;
	}
    /**
     * 设置报文长度
     * @param contentLen
     */
	public void setContentLen(int contentLen) {
		this.contentLen = contentLen;
	}
    /**
     * 获取状态
     * @return
     */
	public int getStatus() {
		return status;
	}
	
    /**
     * 设置状态
     * @param status
     */
	public void setStatus(int status) {
		this.status = status;
	}

}
