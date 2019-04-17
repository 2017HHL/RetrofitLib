package net.huansi.retrofit.bean;

import com.alibaba.fastjson.JSONObject;

/**
 * ExecSql 传过程名和参数
 */
public class ParamExecSqlProcedureBean extends ParamExecSqLBaseBean {
    private String procedureName;//过程名称
    private JSONObject param;


    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public JSONObject getParam() {
        return param;
    }

    public void setParam(JSONObject param) {
        this.param = param;
    }


    @Override
    public JSONObject getBody(){
        JSONObject req=new JSONObject();
        String execsql="";
        req.put("db_name",getDbName());
        StringBuilder sbSQL=new StringBuilder();
        sbSQL.append("EXEC ").append(procedureName).append(" ");
        if (param!=null){
            for (String key : param.keySet()) {
                sbSQL.append(" @").append(key).append("='").append(param.get(key).toString()).append("',");
            }
            execsql=sbSQL.toString().trim().substring(0,sbSQL.toString().trim().length()-1);
        }

        req.put("exec_sql",execsql);
        req.put("before_sql","");
        req.put("after_sql","");
        req.put("error_sql","");

        return req;
    }
}
//EXEC dbo.spppAccessoriesProcess_task_querylist  @sUserNo='', @sMachine='', @sBillNo='sss'