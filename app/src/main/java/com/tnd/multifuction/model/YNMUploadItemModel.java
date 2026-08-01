package com.tnd.multifuction.model;

import com.google.gson.annotations.SerializedName;

public class YNMUploadItemModel {

    @SerializedName("market_code")
    private String marketCode;
    @SerializedName("merchant_name")
    private String merchantName;
    @SerializedName("stall_no")
    private String stallNo;
    @SerializedName("product_type")
    private String productType;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("item")
    private String item;
    @SerializedName("date")
    private String date;
    @SerializedName("count")
    private String count;
    @SerializedName("result")
    private Integer result;
    @SerializedName("handle")
    private String handle;

    public YNMUploadItemModel() {
    }

    public YNMUploadItemModel(String marketCode, String merchantName, String stallNo, String productType, String productName, String item, String date, String count, Integer result, String handle) {
        this.marketCode = marketCode;
        this.merchantName = merchantName;
        this.stallNo = stallNo;
        this.productType = productType;
        this.productName = productName;
        this.item = item;
        this.date = date;
        this.count = count;
        this.result = result;
        this.handle = handle;
    }

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getStallNo() {
        return stallNo;
    }

    public void setStallNo(String stallNo) {
        this.stallNo = stallNo;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }
}
