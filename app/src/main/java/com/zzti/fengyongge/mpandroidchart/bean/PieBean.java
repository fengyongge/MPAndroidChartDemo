package com.zzti.fengyongge.mpandroidchart.bean;

/**
 * describe
 * 饼图本地数据
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/8/12
 */
public class PieBean {

    private String typeName;
    private String orderNum;
    private String level1Id;

    public String getLevel1Id() {
        return level1Id;
    }

    public void setLevel1Id(String level1Id) {
        this.level1Id = level1Id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
