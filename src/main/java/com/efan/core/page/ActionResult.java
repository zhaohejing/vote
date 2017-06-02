package com.efan.core.page;

import java.io.Serializable;

/**
 * 输出类
 */

public class ActionResult implements Serializable {
    private  Boolean success;
    private  Object result;
    private  String error;
    private  boolean unAuthorizedRequest;
    private  String targetUrl;
    public  ActionResult(){
        success=false;
    }
public  ActionResult(Object result){
    this.success=result!=null;
    this.result=result;
}
public ActionResult(Boolean state,Object result){
    this.success=state;
    this.result=result;
}
    public ActionResult(Boolean state,Object result,String msg){
        this.success=state;
        this.result=result;
        this.error=msg;
    }
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isUnAuthorizedRequest() {
        return unAuthorizedRequest;
    }

    public void setUnAuthorizedRequest(boolean unAuthorizedRequest) {
        this.unAuthorizedRequest = unAuthorizedRequest;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
