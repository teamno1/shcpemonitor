package cn.com.afcat.shcpemonitor.channel.socket.server;

import cn.com.afcat.shcpemonitor.common.config.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SocketThread extends Thread {
	
	private static final Logger logger = LoggerFactory.getLogger(SocketThread.class);
	
	
	/**
	 * 系统运行标志
	 */
	private volatile boolean run = true;

	/**
	 * 服务器socket
	 */
	private ServerSocket serverSocket = null;
	/**
	 * jdk线程池
	 */
	private ThreadPoolExecutor executeService = null;
	/**
	 *线程池大小
	 */
	private int corePoolSize = 10;//30
	/**
	 * 线程池最大线程数
	 */
	private int maxNumPoolSize = 15;//50
	/**
	 * 线程生命周期
	 */
	private long keepAliveTime = 300;
	/**
	 * 任务等待队列
	 */
	private int waitTaskQueeSize = 10;//30
	/**
	 * socket缓存队列
	 */
	private int backlog = 10;//30
	/**
	 * 服务端侦听端口
	 */
	private int serverPort = 7810;
	/**
	 * 监听ip地址
	 */
	private String localIp = null;
	/**
	 * 通讯超时时间
	 */
	private int timeout=30;
	/**
	 * 通讯字符集
	 */
	private String charset="UTF-8";
	

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public SocketThread() {
	}

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaxNumPoolSize() {
		return maxNumPoolSize;
	}

	public void setMaxNumPoolSize(int maxNumPoolSize) {
		this.maxNumPoolSize = maxNumPoolSize;
	}

	public long getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	public int getWaitTaskQueeSize() {
		return waitTaskQueeSize;
	}

	public void setWaitTaskQueeSize(int waitTaskQueeSize) {
		this.waitTaskQueeSize = waitTaskQueeSize;
	}

	public int getBacklog() {
		return backlog;
	}

	public void setBacklog(int backlog) {
		this.backlog = backlog;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getLocalIp() {
		return localIp;
	}

	public void setLocalIp(String localIp) {
		this.localIp = localIp;
	}

	public String getCharset() {
		return this.charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * 启动服务器
	 * @return
	 */
	public boolean initServer() {
		try {
			serverPort = Global.getIntegerConfig("server.port", 9000);
			backlog = Global.getIntegerConfig("server.so_backlog",10);
			
			localIp = Global.getConfig("server.local_ip");
			
			corePoolSize = Global.getIntegerConfig("server.core_poolsize",10);
			maxNumPoolSize = Global.getIntegerConfig("server.max_num_poolsize",30);
			keepAliveTime = Global.getIntegerConfig("server.so_keepalive",30);
			waitTaskQueeSize = Global.getIntegerConfig("server.wait_poolsize",30);
			timeout = Global.getIntegerConfig("server.timeout",60000);
			charset = Global.getConfig("server.charset","UTF-8");
			
			if (this.getLocalIp() != null
					&& this.getLocalIp().trim().length() > 0) {
				InetAddress addr = InetAddress.getByName(this.getLocalIp());
				serverSocket = new ServerSocket(this.serverPort, this.backlog,
						addr);
			} else {
				serverSocket = new ServerSocket(this.serverPort, this.backlog);
			}
			serverSocket.setReuseAddress(true);
			// 初始化线程池
			executeService = new ThreadPoolExecutor(this.corePoolSize,
					this.maxNumPoolSize, this.keepAliveTime, TimeUnit.SECONDS,
					new ArrayBlockingQueue<Runnable>(this.waitTaskQueeSize),
					new DiscardPolicy());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 关闭服务器
	 * @return
	 */
	public boolean stopServer() {
		if (executeService != null && executeService.isTerminating()) {
			return true;
		}
		run = false;
		if (executeService != null) {
			executeService.shutdown();
			executeService = null;
		}
		try {
			if (this.serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
				serverSocket = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 任务执行
	 */
	public void run() {
		while (true && serverSocket != null) {
			try {
				// 等待socket连接
				Socket socket = serverSocket.accept();
				if (run) {
					logger.info("receive a connection at local port: "
							+ socket.getLocalPort() + " and remote port"
							+ socket.getPort());
					// 执行一个任务
					executeService.execute(new ServerHandler(socket, this.timeout, this.charset));
				} else {
					socket.close();
					socket = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
