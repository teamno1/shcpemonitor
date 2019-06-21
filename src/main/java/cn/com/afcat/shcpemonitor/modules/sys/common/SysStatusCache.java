package cn.com.afcat.shcpemonitor.modules.sys.common;

import cn.com.afcat.shcpemonitor.common.cache.base.CacheFactory;
import cn.com.afcat.shcpemonitor.common.cache.base.ICache;
import cn.com.afcat.shcpemonitor.common.utils.ApplicationContextHolder;
import cn.com.afcat.shcpemonitor.modules.sys.dao.SysStatusMapper;
import cn.com.afcat.shcpemonitor.modules.sys.entity.SysStatus;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SysStatusCache implements Serializable {

    private ICache sysStsMap= CacheFactory.getCache("sysSts_");//TODO: 常量类替换

    private static SysStatusCache instance=null;

    private final ReentrantReadWriteLock rwl=new ReentrantReadWriteLock();

    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();


    private SysStatusCache(){
        init();
    }

    private void init(){
        SysStatusMapper mapper= ApplicationContextHolder.getBean("sysStatusMapper");
        SysStatus sts= mapper.selectByPrimaryKey(1L);
        sysStsMap.put("sysSts_",sts);
    }


    /**
     * 返回实例，采用启动的时候加载实例，故不需要锁
     * @return
     */
    public static SysStatusCache getInstance(){
        if(instance == null){
            instance=new SysStatusCache();
        }
        return instance;
    }

    /**
     * 刷新内存
     */
    public void refresh(){
        try{
            w.lock();
            sysStsMap.clear();
            init();
        }finally {
            w.unlock();
        }

    }

    /**
     * 对象销毁
     */
    public void destroy(){
        if(instance != null){
            instance.sysStsMap.clear();
        }
        instance = null;
    }



}
