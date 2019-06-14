package cn.com.afcat.shcpemonitor.channel.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TcpComm {
	
	private static final Logger logger = LoggerFactory.getLogger(TcpComm.class);
	/** SSL默认协议 */
	private static final String DEFAULT_SSL_PROTOCOL = "TLSv1";
	private static final int MAX_SHUTDOWN_TRIES = 20;
	/**
	 * 远程地址
	 */
	private InetAddress address = null;
	/**
	 * 端口
	 */
	private int port = -1;
	
	/**
	 * socket句柄
	 */
	private Socket socket = null;

	/**
	 * 输入流
	 */
	private InputStream in = null;

	/**
	 * 输出流
	 */
	private OutputStream out = null;
	/** socket地址 */
	private InetSocketAddress socketAddress = null;
	
	/** ssl工厂类 */
	private SSLSocketFactory sslSocketFactory = null;
	/** 是否使用ssl协议  */
	private boolean sslMode = false;
	private String enabledCipherSuites = null;
	
	public TcpComm(){
	}
	/**
	 * 
	 * @param sock
	 */
	public TcpComm(Socket socket) {
		this.socket = socket;
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("open socket connecting fail", e);
		}
	}

	/**
	 * 连接到指定的ip地址、端口
	 * 
	 * @param sock
	 */
	public TcpComm(String addr, int port) {
		this.setTcpEnv(addr, port);
	}

	/**
	 * 
	 * @param addr
	 * @param port
	 * @return 0 成功 -1失败
	 */
	public int setTcpEnv(String addr, int port) {
		try {
			this.address = InetAddress.getByName(addr);
			this.port = port;
			this.socketAddress = new InetSocketAddress(this.address.getHostAddress(),
					port);
			return 0;
		} catch (UnknownHostException e) {
			logger.error("unknown address", e);
			return -1;
		}
	}

	/**
	 * 关闭socket通讯
	 * 
	 * @return void - 无返回
	 */
	public void close() {
		try {
			if (socket != null) {
				if (in != null) {
					in.close();
					in = null;
				}
				if (out != null) {
					out.close();
					out = null;
				}
				socket.close();
				socket = null;
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 建立socket连接
	 * 
	 * @param addr
	 *            socket服务器地址
	 * @param port
	 *            socket服务器端口
	 * @return 0成功 <0失败
	 */
	public int call(String addr, int port) {
		try {
			this.address = InetAddress.getByName(addr);
			this.port = port;

			this.socketAddress = new InetSocketAddress(this.address.getHostAddress(),
					port);

			// 增加SSL模式
			if (sslMode) {
				socket = getSSLSocket(addr, port);
			} else {
				socket = new Socket();
				socket.setReuseAddress(true);
				socket.connect(socketAddress);
			}
			in = socket.getInputStream();
			out = socket.getOutputStream();
			
			return 0;
		} catch (UnknownHostException e) {
			logger.error("unknown address", e);
			return -1;
		} catch (Exception e) {
			logger.error("socket connection error", e);
			return -2;
		}
	}

	/**
	 * 
	 * @return 0成功 <0失败
	 */
	public int call() {
		if (address == null || port == -1) {
			logger.error("unknown address or port");
			return -1;
		}
		try {
			socket = new Socket();
			socket.setReuseAddress(true);

			socket.setTcpNoDelay(true);
			socket.connect(socketAddress);
			in = socket.getInputStream();
			out = socket.getOutputStream();
			return 0;
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("socket connection error", e);
			return -2;
		}
	}

	/**
	 * 设置通讯超时时间
	 * 
	 * @param seconds
	 *            超时时间
	 * @return 0成功 <0失败
	 */
	public int setTimeOut(int milliseconds) {
		if (socket == null || milliseconds < 0) {
			return -1;
		}
		try {
			socket.setSoTimeout(milliseconds);
		} catch (Exception e) {
			logger.error("setting timeout error", e);
			return -2;
		}

		return milliseconds;
	}

	/**
	 * 
	 * @param b
	 * @return >0返回发送的字节数 <0发送失败
	 */
	public int sendMsg(byte[] b) {
		try {
			// 设置通讯超时时间
			out.write(b);
			out.flush();

			return b.length;
		} catch (Exception e) {
			logger.error("send draft fail", e);
			return -1;
		}
	}

	/**
	 * 
	 * @param pack
	 * @return >0返回发送的字节数 <0发送失败
	 */
	public int sendPack(TcpPackage pack) {
		try {
			out.write(pack.getContent());
			out.flush();
		} catch (IOException e) {
			pack.setStatus(TcpPackage.STATUS_TCP_FAIL);
			logger.error("send draft fail", e);
			return -1;
		}

		return pack.getContent().length;
	}

	/**
	 * 接收指定字节数的数据
	 * 
	 * @param recvLen
	 *            要接收的字节长度
	 * @return byte[] - 收到的包的内容
	 */
	public TcpPackage recvPack(int recvLen) {
		TcpPackage pack = new TcpPackage();
		try {
			byte[] recv = new byte[recvLen];
			int tempLen = recvLen > 128 ? 128 : recvLen;
			byte[] buf = new byte[tempLen];

			int hasRecv = 0;
			int needRecv = recvLen;

			while (true) {
				int iLen = in.read(buf); // 接收包体
				if (iLen == -1)
					break;
				System.arraycopy(buf, 0, recv, hasRecv, iLen);
				hasRecv += iLen; // 已经接收这么多字节
				needRecv -= iLen;

				if (needRecv > 0 && needRecv < tempLen) {
					buf = null;
					buf = new byte[needRecv];
				}

				if (hasRecv >= recvLen) {
					break;
				}
			}
			pack.setContent(recv);
			pack.setContentLen(hasRecv);

		} catch (InterruptedIOException e) {
			pack.setStatus(TcpPackage.STATUS_TCP_TIME_OUT);
			logger.error("receive draft fail", e);
		} catch (IOException e) {
			pack.setStatus(TcpPackage.STATUS_TCP_FAIL);
			logger.error("receive draft fail", e);
		}
		return pack;
	}

	/**
	 * 关闭输入流
	 */
	public void shutdownInput() {
		try {
			int available = in.available();
			int count = 0;
			while (available > 0 && count++ < MAX_SHUTDOWN_TRIES) {
				in.skip(available);
				available = in.available();
			}
		} catch (NullPointerException npe) {
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建SSL客户端连接
	 * 
	 * @param hostAddress
	 * @return
	 */
	private Socket getSSLSocket(String addr, int port) {
		SSLSocket resultSocket = null;
		try {
			if (sslSocketFactory == null)
				throw new Exception(
						"ssl客户端不存在!");

			resultSocket = (SSLSocket) sslSocketFactory.createSocket(addr, port);
			resultSocket
					.setEnabledProtocols(new String[] { DEFAULT_SSL_PROTOCOL });
			resultSocket.setTcpNoDelay(true);
			if(enabledCipherSuites!=null && enabledCipherSuites.trim().length()>0){
				resultSocket.setEnabledCipherSuites(enabledCipherSuites.split(","));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultSocket;
	}
	
	public SSLSocketFactory getSslSocketFactory() {
		return sslSocketFactory;
	}
	public void setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
		this.sslSocketFactory = sslSocketFactory;
	}
	public boolean getSslMode() {
		return sslMode;
	}
	public void setSslMode(boolean sslMode) {
		this.sslMode = sslMode;
	}
	
	public String getEnabledCipherSuites() {
		return enabledCipherSuites;
	}

	public void setEnabledCipherSuites(String enabledCipherSuites) {
		this.enabledCipherSuites = enabledCipherSuites;
	}

}

