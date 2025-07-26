package com.tnd.multifuction.bean;

import java.util.List;

/**
 * @author :created by zsl
 * 时间:2020/10/9 09
 * 描述:
 */
public class SHData {
    private String errCode;
    private String errMsg;
    private List<ItemsBean> items;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {

        private String eid;
        private String name;
        private String ename;
        private String stall;
        private String saleType;

        public String getEid() {
            return eid;
        }

        public void setEid(String eid) {
            this.eid = eid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
    }
}
