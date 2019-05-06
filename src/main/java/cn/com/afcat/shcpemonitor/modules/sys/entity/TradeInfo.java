package cn.com.afcat.shcpemonitor.modules.sys.entity;

public class TradeInfo {
    private Long id;

    private String memberNo;

    private String memberName;

    private String brchNo;

    private String brchName;

    private String tradeUserNo;

    private String tradeUserName;

    private String settleAccountNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo == null ? null : memberNo.trim();
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getBrchNo() {
        return brchNo;
    }

    public void setBrchNo(String brchNo) {
        this.brchNo = brchNo == null ? null : brchNo.trim();
    }

    public String getBrchName() {
        return brchName;
    }

    public void setBrchName(String brchName) {
        this.brchName = brchName == null ? null : brchName.trim();
    }

    public String getTradeUserNo() {
        return tradeUserNo;
    }

    public void setTradeUserNo(String tradeUserNo) {
        this.tradeUserNo = tradeUserNo == null ? null : tradeUserNo.trim();
    }

    public String getTradeUserName() {
        return tradeUserName;
    }

    public void setTradeUserName(String tradeUserName) {
        this.tradeUserName = tradeUserName == null ? null : tradeUserName.trim();
    }

    public String getSettleAccountNo() {
        return settleAccountNo;
    }

    public void setSettleAccountNo(String settleAccountNo) {
        this.settleAccountNo = settleAccountNo == null ? null : settleAccountNo.trim();
    }
}