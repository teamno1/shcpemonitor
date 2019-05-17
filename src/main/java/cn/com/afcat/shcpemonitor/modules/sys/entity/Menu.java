package cn.com.afcat.shcpemonitor.modules.sys.entity;

public class Menu {
    private String menuCode;

    private String menuName;

    private String menuUrl;

    private String menuType;

    private String menuLevel;

    private String menuIcon;

    private Integer orderNo;

    private String parentMenuCode;

    private String openFlag;

    private String treeIdx;

    private String logonFlag;

    private String iconDisplay;

    private String menuClass;

    private String remark;

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode == null ? null : menuCode.trim();
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType == null ? null : menuType.trim();
    }

    public String getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(String menuLevel) {
        this.menuLevel = menuLevel == null ? null : menuLevel.trim();
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon == null ? null : menuIcon.trim();
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getParentMenuCode() {
        return parentMenuCode;
    }

    public void setParentMenuCode(String parentMenuCode) {
        this.parentMenuCode = parentMenuCode == null ? null : parentMenuCode.trim();
    }

    public String getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(String openFlag) {
        this.openFlag = openFlag == null ? null : openFlag.trim();
    }

    public String getTreeIdx() {
        return treeIdx;
    }

    public void setTreeIdx(String treeIdx) {
        this.treeIdx = treeIdx == null ? null : treeIdx.trim();
    }

    public String getLogonFlag() {
        return logonFlag;
    }

    public void setLogonFlag(String logonFlag) {
        this.logonFlag = logonFlag == null ? null : logonFlag.trim();
    }

    public String getIconDisplay() {
        return iconDisplay;
    }

    public void setIconDisplay(String iconDisplay) {
        this.iconDisplay = iconDisplay == null ? null : iconDisplay.trim();
    }

    public String getMenuClass() {
        return menuClass;
    }

    public void setMenuClass(String menuClass) {
        this.menuClass = menuClass == null ? null : menuClass.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}