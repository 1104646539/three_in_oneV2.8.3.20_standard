package com.tnd.multifuction.model;

public class YNMLoginModel {

    /**
     * access_token : 9e4e8f4f-f569-4e14-9143-e9605f3ca8c4
     * token_type : bearer
     * expires_in : 39428
     * scope : all
     */

    private String access_token;
    private String token_type;
    private int expires_in;
    private String scope;
    /**
     * error : invalid_client
     * error_description : Bad client credentials
     */

    private String errCode;
    private String errMsg;
    private String error;
    private String error_description;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
}
