package net.huansi.retrofit.bean;

import com.alibaba.fastjson.JSONObject;

/**
 * ExecSql
 */
public abstract class ParamExecSqLBaseBean extends ParamBaseBean {
    public enum RequestType{
        POST,
        GET
    }

    private RequestType requestType;//post get请求类型
    private String dbName;//数据库名
    private boolean isQuery;

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public boolean isQuery() {
        return isQuery;
    }

    public void setQuery(boolean query) {
        isQuery = query;
    }

    public abstract JSONObject getBody();
}
