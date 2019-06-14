package cn.com.afcat.shcpemonitor.common.cache.xmemcached;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class XMemCachedClientManager {
	private static MemcachedClientBuilder builder = null;
	private static MemcachedClient mcc = null;
	private static List<InetSocketAddress> address = new ArrayList<InetSocketAddress>();
	// 这里可以读取配置文件
	private static List<String> serversList = new ArrayList<String>();

	/** The cfg. */
	private static String memcacheXml = "memcached.xml";
	private static XMemcachedConfig config;
	private static XMemcachedSocketPoolConfig socketPoolConfig;

	private static void loadProperties() {
		// 初始化config配置文件
		config = new XMemcachedConfig();
		socketPoolConfig = new XMemcachedSocketPoolConfig();
		try {
			ClassLoader loader;
			XMLInputFactory factory;
			URL url = null;
			loader = Thread.currentThread().getContextClassLoader();
			url = loader.getResource(memcacheXml);
			factory = XMLInputFactory.newInstance();
			if (url == null) {
				throw new RuntimeException("no memcached config find! please put memcached.xml in your classpath");
			}
			loadMemcachedConfigFromUrl(url, factory);
		} catch (Exception ex) {
			throw new RuntimeException("MemcachedManager loadConfig error !", ex);
		}
	}

	private static void loadMemcachedConfigFromUrl(URL url, XMLInputFactory factory) {
		InputStream in = null;
		XMLEventReader r = null;
		try {
			in = url.openStream();
			r = factory.createXMLEventReader(in);
			while (r.hasNext()) {
				XMLEvent event = r.nextEvent();
				if (event.isStartElement()) {
					StartElement start = event.asStartElement();
					String tag = start.getName().getLocalPart();

					// 解析XML中client元素
					if (tag.equalsIgnoreCase("client")) {
						if (start.getAttributeByName(new QName("", "name")) != null)
							config.setName(start.getAttributeByName(new QName("", "name")).getValue());
						else {
							throw new RuntimeException("memcached client name can't not be null!");
						}
						if (start.getAttributeByName(new QName("", "socketpool")) != null)
							config.setSocketPool(start.getAttributeByName(new QName("", "socketpool")).getValue());
						else {
							throw new RuntimeException("memcached client socketpool can't not be null!");
						}
						if (start.getAttributeByName(new QName("", "compressEnable")) != null) {
							config.setCompressEnable(Boolean.parseBoolean(
									start.getAttributeByName(new QName("", "compressEnable")).getValue()));
						} else {
							config.setCompressEnable(true);
						}
						if (start.getAttributeByName(new QName("", "defaultEncoding")) != null) {
							config.setDefaultEncoding(
									start.getAttributeByName(new QName("", "defaultEncoding")).getValue());
						} else {
							config.setDefaultEncoding("UTF-8");
						}
						if (start.getAttributeByName(new QName("", "opTimeout")) != null) {
							config.setOpTimeout(
									Long.parseLong(start.getAttributeByName(new QName("", "opTimeout")).getValue()));
						} else {
							config.setOpTimeout(0);
						}
					}

					// 设置错误处理类
					if ((tag.equalsIgnoreCase("errorHandler")) && (config != null)) {
						event = r.peek();
						if (!(event.isCharacters()))
							continue;
						config.setErrorHandler(event.asCharacters().getData());
						r.nextEvent();
					}

					// 解析socketPool
					if (tag.equalsIgnoreCase("socketpool")) {
						if (start.getAttributeByName(new QName("", "name")) != null)
							socketPoolConfig.setName(start.getAttributeByName(new QName("", "name")).getValue());
						else {
							throw new RuntimeException("memcached client socketpool name can't not be null!");
						}
						if (start.getAttributeByName(new QName("", "failover")) != null) {
							socketPoolConfig.setFailover(Boolean
									.parseBoolean(start.getAttributeByName(new QName("", "failover")).getValue()));
						}

						if (start.getAttributeByName(new QName("", "initConn")) != null) {
							socketPoolConfig.setInitConn(
									Integer.parseInt(start.getAttributeByName(new QName("", "initConn")).getValue()));
						}

						if (start.getAttributeByName(new QName("", "minConn")) != null) {
							socketPoolConfig.setMinConn(
									Integer.parseInt(start.getAttributeByName(new QName("", "minConn")).getValue()));
						}

						if (start.getAttributeByName(new QName("", "maxConn")) != null) {
							socketPoolConfig.setMaxConn(
									Integer.parseInt(start.getAttributeByName(new QName("", "maxConn")).getValue()));
						}

						if (start.getAttributeByName(new QName("", "maintSleep")) != null) {
							socketPoolConfig.setMaintSleep(
									Integer.parseInt(start.getAttributeByName(new QName("", "maintSleep")).getValue()));
						}

						if (start.getAttributeByName(new QName("", "nagle")) != null) {
							socketPoolConfig.setNagle(
									Boolean.parseBoolean(start.getAttributeByName(new QName("", "nagle")).getValue()));
						}

						if (start.getAttributeByName(new QName("", "socketTO")) != null) {
							socketPoolConfig.setSocketTo(
									Integer.parseInt(start.getAttributeByName(new QName("", "socketTO")).getValue()));
						}

						if (start.getAttributeByName(new QName("", "maxIdle")) != null) {
							socketPoolConfig.setMaxIdle(
									Integer.parseInt(start.getAttributeByName(new QName("", "maxIdle")).getValue()));
						}

						if (start.getAttributeByName(new QName("", "aliveCheck")) != null)
							;
						socketPoolConfig.setAliveCheck(
								Boolean.parseBoolean(start.getAttributeByName(new QName("", "aliveCheck")).getValue()));
					}

					if ((tag.equalsIgnoreCase("servers")) && (socketPoolConfig != null)) {
						event = r.peek();

						if (!(event.isCharacters()))
							continue;
						socketPoolConfig.setServers(event.asCharacters().getData());
						r.nextEvent();
					}

					if ((tag.equalsIgnoreCase("weights")) && (socketPoolConfig != null)) {
						event = r.peek();

						if (!(event.isCharacters()))
							continue;
						socketPoolConfig.setWeights(event.asCharacters().getData());
						r.nextEvent();
					}
				}
//				if (!(event.isEndElement()))
//					break;
			}

			try {
				if (r != null) {
					r.close();
				}
				if (in != null) {
					in.close();
				}
				r = null;
				in = null;
			} catch (Exception ex) {
				throw new RuntimeException("processConfigURL error !", ex);
			}
		} catch (Exception e) {
			try {
				if (r != null) {
					r.close();
				}
				if (in != null) {
					in.close();
				}
				r = null;
				in = null;
			} catch (Exception ex) {
				throw new RuntimeException("processConfigURL error !", ex);
			}
		} finally {
			try {
				if (r != null) {
					r.close();
				}
				if (in != null) {
					in.close();
				}
				r = null;
				in = null;
			} catch (Exception ex) {
				throw new RuntimeException("processConfigURL error !", ex);
			}
		}
	}

	private static void init() {
		if (socketPoolConfig.getServers() != null && !"".equals(socketPoolConfig.getServers())) {
			String servers = socketPoolConfig.getServers();
			String[] serversArray = servers.split(",");
			for (int i = 0; i < serversArray.length; i++) {
				serversList.add(serversArray[i]);
				address.add(AddrUtil.getOneAddress(serversArray[i]));
			}
		} else {
			throw new RuntimeException("no serverAddress !");
		}

		builder = new XMemcachedClientBuilder(address);
		builder.setConnectionPoolSize(socketPoolConfig.getMaxConn());
		builder.setName(config.getName());
		/*if (config.getOpTimeout() != 0) {
			builder.setOpTimeout(config.getOpTimeout());
		}*/
		builder.setFailureMode(socketPoolConfig.isFailover());
		// 启用二进制协议，但如果设置为二进制，则不能查找所有keys（特别注意）
		// builder.setCommandFactory(new BinaryCommandFactory());
		// 设置分布策略，一致性哈希net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator或者ArraySessionLocator(默认)
		builder.setSessionLocator(new KetamaMemcachedSessionLocator(true));
		
		try{
			mcc = builder.build();
		}catch(IOException e){
			throw new RuntimeException(e.getMessage());
		}
	}

	static {
		loadProperties();
		init();
	}

	public static MemcachedClient getClient() {
		return mcc;
	}
	
	public static List<String> getServersList() {
		return serversList;
	}

}
