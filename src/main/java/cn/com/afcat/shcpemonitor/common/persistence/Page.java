package cn.com.afcat.shcpemonitor.common.persistence;

import cn.com.afcat.shcpemonitor.common.config.Global;

import java.util.List;

public class Page<T> {

    private int pageSize=Integer.valueOf(Global.getConfig("page.pageSize"));
    private int pageNo = 1; // 当前页码
    private long count;// 总记录数，设置为“-1”表示不查询总数

    private List<T> list;//结果
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }



    /**
     * 获取 Hibernate FirstResult
     */
    public int getFirstResult(){
        int firstResult = (getPageNo() - 1) * getPageSize();
        if (firstResult >= getCount()) {
            firstResult = 0;
        }
        return firstResult;
    }
    /**
     * 获取 Hibernate MaxResults
     */
    public int getMaxResults(){
        return getPageSize();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
