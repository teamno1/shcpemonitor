package cn.com.afcat.shcpemonitor.modules.sys.entity;

public class SysStatus {
    private Long id;

    private String curDate;

    private String workSeq;

    private String dayEndTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurDate() {
        return curDate;
    }

    public void setCurDate(String curDate) {
        this.curDate = curDate == null ? null : curDate.trim();
    }

    public String getWorkSeq() {
        return workSeq;
    }

    public void setWorkSeq(String workSeq) {
        this.workSeq = workSeq == null ? null : workSeq.trim();
    }

    public String getDayEndTime() {
        return dayEndTime;
    }

    public void setDayEndTime(String dayEndTime) {
        this.dayEndTime = dayEndTime == null ? null : dayEndTime.trim();
    }
}