package net.huansi.retrofit.netmanage;

import net.huansi.retrofit.bean.ParamExecSqLBaseBean;
import net.huansi.retrofit.listener.WebResponseListener.WebResponseError;
import net.huansi.retrofit.netapi.HttpApi;
import net.huansi.retrofit.utils.DefaultObserver;
import net.huansi.retrofit.utils.RxUtil;
import net.huansi.retrofit.listener.WebResponseListener;

import java.io.IOException;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2019/3/16 0016.
 */

public class ExecSqlManager extends BaseManager{

    public ExecSqlManager(HttpApi httpApi) {
        super(httpApi);
    }


    /**
     * 执行execSql  过程
     */
    public <Bean extends ParamExecSqLBaseBean> void execQuerySql(Bean bean, final WebResponseListener<String> responseListener) {
        Observable<ResponseBody> observable=null;
        if (bean.getRequestType()==null)return;
        switch (bean.getRequestType()){
            case GET:
                observable = httpApi.execGetSql(bean.getToken(), bean.getBody());
                break;
            case POST:
                if(bean.isQuery()){
                    observable=httpApi.querySql(bean.getToken(),bean.getBody());
                }else {
                    observable = httpApi.execPostSql(bean.getToken(), bean.getBody());
                }
                break;
        }
        if(observable==null) return;
        observable.compose(RxUtil.<ResponseBody>rxSchedulerHelper())
                .subscribe(new DefaultObserver<ResponseBody>(responseListener) {
                    @Override
                    public void onSuccess(ResponseBody response) {
                        try {
                            String json=response.string();
                            if(responseListener != null){
                                //判断json字符串中是否有error
                                boolean isError = json.contains("error");
                                if(isError){
                                    responseListener.error(new WebResponseError(json));
                                }else{
                                    responseListener.success(json);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            responseListener.error(new WebResponseError("异常"));
                        }
                    }
                });
    }

//    /**
//     * 执行execSql
//     */
//    private void execSql(ParamExecSqlProcedureBean paramExecSqlBean,
//                         boolean isQuery,
//                         final WebResponseListener webResponseListener) {
//        if (paramExecSqlBean.isAsync()){
//            if (paramExecSqlBean.isRequestType()){
//                if (isQuery){
//                    //同步 post 查询
//                    httpApi.execPostSql(paramExecSqlBean,paramExecSqlBean.getsToken())
//                            .compose(RxUtil.<ResponseBody>rxSchedulerHelper())
//                            .subscribe(new DefaultObserver<ResponseBody>(webResponseListener) {
//                                @Override
//                                public void onSuccess(ResponseBody response) {
//                                    try {
//                                        String json=response.string();
//                                        if(webResponseListener != null){
//                                            JSONObject jsonObject = JSON.parseObject(json);
//                                            //判断json字符串中是否有error
//                                            boolean isError = json.contains("error");
//                                            if(isError){
//                                                webResponseListener.error(jsonObject.get("error_detail").toString());
//                                            }else{
//                                                webResponseListener.success(json);
//                                            }
//                                        }
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                        webResponseListener.error(e.getMessage());
//                                    }
//                                }
//                            });
//                }else
//                    {
//                        mHttpApi.execQueryPostSql(paramExecSqlBean,paramExecSqlBean.getsToken())
//                                .compose(RxUtil.<ResponseBody>rxSchedulerHelper())
//                                .subscribe(new DefaultObserver<ResponseBody>(webResponseListener) {
//                                    @Override
//                                    public void onSuccess(ResponseBody response) {
//                                        try {
//                                            String json=response.string();
//                                            if(webResponseListener != null){
//                                                JSONObject jsonObject = JSON.parseObject(json);
//                                                //判断json字符串中是否有error
//                                                boolean isError = json.contains("error");
//                                                if(isError){
//                                                    webResponseListener.error(jsonObject.get("error_detail").toString());
//                                                }else{
//                                                    webResponseListener.success(json);
//                                                }
//                                            }
//                                        } catch (IOException e) {
//                                            e.printStackTrace();
//                                            webResponseListener.error(e.getMessage());
//                                        }
//                                    }
//                                });
//                    }
//
//            }else {
//                if (isQuery){
//                    mHttpApi.execPostSql(paramExecSqlBean,paramExecSqlBean.getsToken())
//                            .compose(RxUtil.<ResponseBody>rxSchedulerHelper())
//                            .subscribe(new DefaultObserver<ResponseBody>(webResponseListener) {
//                                @Override
//                                public void onSuccess(ResponseBody response) {
//                                    try {
//                                        String json=response.string();
//                                        if(webResponseListener != null){
//                                            JSONObject jsonObject = JSON.parseObject(json);
//                                            //判断json字符串中是否有error
//                                            boolean isError = json.contains("error");
//                                            if(isError){
//                                                webResponseListener.error(jsonObject.get("error_detail").toString());
//                                            }else{
//                                                webResponseListener.success(json);
//                                            }
//                                        }
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                        webResponseListener.error(e.getMessage());
//                                    }
//                                }
//                            });
//                }else
//                {
//                    mHttpApi.execQueryPostSql(paramExecSqlBean,paramExecSqlBean.getsToken())
//                            .compose(RxUtil.<ResponseBody>rxSchedulerHelper())
//                            .subscribe(new DefaultObserver<ResponseBody>(webResponseListener) {
//                                @Override
//                                public void onSuccess(ResponseBody response) {
//                                    try {
//                                        String json=response.string();
//                                        if(webResponseListener != null){
//                                            JSONObject jsonObject = JSON.parseObject(json);
//                                            //判断json字符串中是否有error
//                                            boolean isError = json.contains("error");
//                                            if(isError){
//                                                webResponseListener.error(jsonObject.get("error_detail").toString());
//                                            }else{
//                                                webResponseListener.success(json);
//                                            }
//                                        }
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                        webResponseListener.error(e.getMessage());
//                                    }
//                                }
//                            });
//                }
//            }
//
//        }else {
//            if (paramExecSqlBean.isRequestType()){
//                mHttpApi.execAsyncPostcSql(paramExecSqlBean,paramExecSqlBean.getsToken())
//                        .equals(new Callback<ResponseBody>() {
//                            @Override
//                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                String json=response.body().toString();
//                                if (webResponseListener !=null&&!json.equals("")) {
//                                    JSONObject jsonObject= JSON.parseObject(json);
//                                    Object val = jsonObject.get("error");
//                                    if (val == null) {
//                                        webResponseListener.success(json);
//                                    }else {
//                                        webResponseListener.error(val.toString());
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                if (webResponseListener !=null) {
//                                    webResponseListener.error(t.toString());
//                                }
//                            }
//                        });
//            }else {
//                mHttpApi.execAsyncGetcSql(paramExecSqlBean,paramExecSqlBean.getsToken())
//                        .equals(new Callback<ResponseBody>() {
//                            @Override
//                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                String json=response.body().toString();
//                                if (webResponseListener !=null&&!json.equals("")) {
//                                    JSONObject jsonObject=JSON.parseObject(json);
//                                    Object val = jsonObject.get("error");
//                                    if (val == null) {
//                                        webResponseListener.success(json);
//                                    }else {
//                                        webResponseListener.error(val.toString());
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                if (webResponseListener !=null) {
//                                    webResponseListener.error(t.toString());
//                                }
//                            }
//                        });
//            }
//
//        }
//
//    }
//
////    /**
////     * 执行execSql  过程
////     */
////    public void execSql(ParamExecSqlProcedureBean bean,final WebResponseListener<String> responseListener) {
////        httpApi.execPostSql(bean.getToken(),bean.getBody())
////                .compose(RxUtil.<ResponseBody>rxSchedulerHelper())
////                .subscribe(new DefaultObserver<ResponseBody>(responseListener) {
////                    @Override
////                    public void onSuccess(ResponseBody response) {
////                        try {
////                            String json=response.string();
////                            if(responseListener != null){
////                                //判断json字符串中是否有error
////                                boolean isError = json.contains("error");
////                                if(isError){
////                                    responseListener.error(new WebResponseListener.WebResponseError(-1,json));
////                                }else{
////                                    responseListener.success(json);
////                                }
////                            }
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                            responseListener.error(new WebResponseListener.WebResponseError("异常"));
////                        }
////                    }
////                });
////    }
//
//
//
//
//
//    /**
//     * 执行execSql
//     */
//    public  void execSql(String procedureName,JSONObject req, final WebResponseListener webResponseListener) {
//        String sql="EXEC "+procedureName;
//        String execSql= CompressUtils.getPara(sql,req);
//        ParamExecSqlProcedureBean paramExecSqlBean =new ParamExecSqlProcedureBean();
//        paramExecSqlBean.setsExecSql(execSql);
//        paramExecSqlBean.setAsync(true);
//        paramExecSqlBean.setRequestType(true);
//        paramExecSqlBean.setsDbName("");
//        paramExecSqlBean.setsBeforeSql("");
//        paramExecSqlBean.setsAfterSql("");
//        paramExecSqlBean.setsErrorSql("");
//        execSql(paramExecSqlBean,true, webResponseListener);
//    }
//    /**
//     * 执行整个Sql语句
//     * @param sql
//     */
//    public  void execSql(String sql, String[] paramStrings,WebResponseListener webResponseListener) {
//        if (paramStrings == null) paramStrings = new String[]{};
//        sql = String.format(sql, paramStrings);
//        ParamExecSqlProcedureBean paramExecSqlBean =new ParamExecSqlProcedureBean();
//        paramExecSqlBean.setsExecSql(sql);
//        paramExecSqlBean.setAsync(true);
//        paramExecSqlBean.setRequestType(true);
//        paramExecSqlBean.setsDbName("");
//        paramExecSqlBean.setsBeforeSql("");
//        paramExecSqlBean.setsAfterSql("");
//        paramExecSqlBean.setsErrorSql("");
//        execSql(paramExecSqlBean,true, webResponseListener);
//    }
//
//    /**
//     * 执行整个Sql语句
//     * @param sql
//     */
//    public  void execSql(String sql,WebResponseListener webResponseListener) {
//        ParamExecSqlProcedureBean paramExecSqlBean =new ParamExecSqlProcedureBean();
//        paramExecSqlBean.setsExecSql(sql);
//        paramExecSqlBean.setAsync(true);
//        paramExecSqlBean.setRequestType(true);
//        paramExecSqlBean.setsDbName("");
//        paramExecSqlBean.setsBeforeSql("");
//        paramExecSqlBean.setsAfterSql("");
//        paramExecSqlBean.setsErrorSql("");
//        execSql(paramExecSqlBean,true, webResponseListener);
//    }
//
//    /**
//     * 执行execSql
//     * @param paramExecSqlBean 比如："EXEC spappUpdate @sUserNo='zhangsan',@sPwd='123';"  4
//     *                或者 "UPDATE ....."
//     */
//    public  void querySql(ParamExecSqlProcedureBean paramExecSqlBean, JSONObject jsonObject, WebResponseListener webResponseListener) {
//        String execSql= CompressUtils.getPara(paramExecSqlBean.getsExecSql(),jsonObject);
//        paramExecSqlBean.setsExecSql(execSql);
//        execSql(paramExecSqlBean,false, webResponseListener);
//    }
//
//    /**
//     * 执行execSql
//     */
//    public  void querySql(String procedureName,JSONObject req, final WebResponseListener webResponseListener) {
//        String sql="EXEC "+procedureName;
//        String execSql= CompressUtils.getPara(sql,req);
//        ParamExecSqlProcedureBean paramExecSqlBean =new ParamExecSqlProcedureBean();
//        paramExecSqlBean.setsExecSql(execSql);
//        paramExecSqlBean.setAsync(true);
//        paramExecSqlBean.setRequestType(true);
//        paramExecSqlBean.setsDbName("");
//        paramExecSqlBean.setsBeforeSql("");
//        paramExecSqlBean.setsAfterSql("");
//        paramExecSqlBean.setsErrorSql("");
//        execSql(paramExecSqlBean,false, webResponseListener);
//    }
//    /**
//     * 执行整个Sql语句
//     * @param sql
//     */
//    public  void querySql(String sql, String[] paramStrings,WebResponseListener webResponseListener) {
//        if (paramStrings == null) paramStrings = new String[]{};
//        sql = String.format(sql, paramStrings);
//        ParamExecSqlProcedureBean paramExecSqlBean =new ParamExecSqlProcedureBean();
//        paramExecSqlBean.setsExecSql(sql);
//        paramExecSqlBean.setAsync(true);
//        paramExecSqlBean.setRequestType(true);
//        paramExecSqlBean.setsDbName("");
//        paramExecSqlBean.setsBeforeSql("");
//        paramExecSqlBean.setsAfterSql("");
//        paramExecSqlBean.setsErrorSql("");
//        execSql(paramExecSqlBean,false, webResponseListener);
//    }
//
//    /**
//     * query执行整个Sql语句
//     * @param sql
//     */
//    public  void querySql(String sql,WebResponseListener webResponseListener) {
//        ParamExecSqlProcedureBean paramExecSqlBean =new ParamExecSqlProcedureBean();
//        paramExecSqlBean.setsExecSql(sql);
//        paramExecSqlBean.setAsync(true);
//        paramExecSqlBean.setRequestType(true);
//        paramExecSqlBean.setsDbName("");
//        paramExecSqlBean.setsBeforeSql("");
//        paramExecSqlBean.setsAfterSql("");
//        paramExecSqlBean.setsErrorSql("");
//        execSql(paramExecSqlBean,false, webResponseListener);
//    }


}
