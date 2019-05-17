package cn.com.afcat.shcpemonitor.channel.amq;

import cn.com.afcat.shcpemonitor.common.config.Global;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * 异步消息监听的管理类
 * @author litao
 */
public class AmqMessageListenerContainer extends DefaultMessageListenerContainer {
    private String listenSwitch;

    public void initiallize(){
        if(Global.NO.equals(getListenSwitch())){
            super.setConnectionFactory(null);
        }
        super.initialize();
    }


    public String getListenSwitch() {
        return listenSwitch;
    }

    public void setListenSwitch(String listenSwitch) {
        this.listenSwitch = listenSwitch;
    }
}
