package com.lsgf.pojo;

public class RspBean {
    public int rspCode;
    public String rspMsg;

    public RspBean(){

    }
    public RspBean(int rspCode, String rspMsg){
        this.rspCode=rspCode;
        this.rspMsg=rspMsg;
    }
}
