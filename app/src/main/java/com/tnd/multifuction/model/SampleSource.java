package com.tnd.multifuction.model;

/**
 * 商品来源
 */
public class SampleSource extends UserInputModel<SampleSource> implements FiltrateModel{
    //id
    public int ss_id;

    private String eid;
    //name
    public String ss_name;

    private String ename;

    private String stall;

    private String saleType;

    private boolean isSelect;
    public SampleSource() {
    }
    @Override
    public String toString() {
        return ss_name==null?"":ss_name;
    }
    public SampleSource(String ss_name) {
        this.ss_name = ss_name;
    }

    @Override
    public String getName() {
        return ss_name;
    }


    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public void setSs_name(String ss_name) {
        this.ss_name = ss_name;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getStall() {
        return stall;
    }

    public void setStall(String stall) {
        this.stall = stall;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    @Override
    public boolean isSelect() {
        return isSelect;
    }

    @Override
    public void setSelect(boolean isSelect) {
    }

}
