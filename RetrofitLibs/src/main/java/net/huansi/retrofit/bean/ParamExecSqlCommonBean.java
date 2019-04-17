package net.huansi.retrofit.bean;

import com.alibaba.fastjson.JSONObject;

/**
 * ExecSql
 */
public class ParamExecSqlCommonBean extends ParamExecSqLBaseBean {

    private String dbName;//数据库名
    private String execSql;//执行的sql语句
    private String beforeSql;//执行前的sql语句
    private String afterSql;//执行后的sql语句
    private String errorSql;//错误的sql语句

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getExecSql() {
        return execSql;
    }

    public void setExecSql(String execSql) {
        this.execSql = execSql;
    }

    public String getBeforeSql() {
        return beforeSql;
    }

    public void setBeforeSql(String beforeSql) {
        this.beforeSql = beforeSql;
    }

    public String getAfterSql() {
        return afterSql;
    }

    public void setAfterSql(String afterSql) {
        this.afterSql = afterSql;
    }

    public String getErrorSql() {
        return errorSql;
    }

    public void setErrorSql(String errorSql) {
        this.errorSql = errorSql;
    }

    public JSONObject getBody(){
        JSONObject req=new JSONObject();
        req.put("db_name",dbName);
        req.put("exec_sql",execSql);
        req.put("before_sql",beforeSql);
        req.put("after_sql",afterSql);
        req.put("error_sql",errorSql);
        return req;
    }
}
