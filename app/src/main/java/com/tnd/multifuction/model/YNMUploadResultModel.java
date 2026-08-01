package com.tnd.multifuction.model;

public class YNMUploadResultModel {

    /**
     * 1 成功
     */
    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
