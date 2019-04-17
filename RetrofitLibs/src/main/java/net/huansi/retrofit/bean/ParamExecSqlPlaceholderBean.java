package net.huansi.retrofit.bean;

import com.alibaba.fastjson.JSONObject;

/**
 * ExecSql 执行整个sql
 */
public class ParamExecSqlPlaceholderBean extends ParamExecSqLBaseBean {
    private String sql;//过程名称
    private String[] placeholderArray;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String[] getPlaceholderArray() {
        return placeholderArray;
    }

    public void setPlaceholderArray(String[] placeholderArray) {
        this.placeholderArray = placeholderArray;
    }

    @Override
    public JSONObject getBody(){
        JSONObject req=new JSONObject();
        req.put("db_name",getDbName());

        if(placeholderArray==null) {
            req.put("exec_sql",sql);
        }else {
            req.put("exec_sql",String.format(sql,placeholderArray));
        }
        req.put("before_sql","");
        req.put("after_sql","");
        req.put("error_sql","");
        return req;
    }
}
