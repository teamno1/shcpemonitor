package cn.com.afcat.shcpemonitor.channel;

/**
 * 消息发送客户端
 * @author litao
 */
public interface IMsgSendClient {

    /**
     * 发送消息
     * @param msg
     * @return
     */
    public String sendMessage(String msg)throws Exception;

}
