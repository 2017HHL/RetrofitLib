package net.huansi.retrofit.netapi;


import com.alibaba.fastjson.JSONObject;

import net.huansi.retrofit.bean.ParamExecSqlProcedureBean;
import net.huansi.retrofit.bean.ParamSendMQBean.ParamSendMQDataBean;
import net.huansi.retrofit.bean.ResultTokenBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;


/**
 * 同步网络请求
 * 存放所有的Api
 */
public interface HttpApi {
    /**
     * 同步调用方法
     * @param req
     * @return
     */
    @POST("authAPI/authorize?tenant=tec&type=1&app=mes")
    Observable<ResultTokenBean> getToken(@Body JSONObject req);

    @POST("mq/{exchange}/{key}/")
    Observable<ResponseBody> sendMQmsg(@Header("Authorization") String token,
                                       @Path("exchange") String exchange,
                                       @Path("key") String key,
                                       @Body ParamSendMQDataBean bean);


    @POST("web_query/")
    Observable<ResponseBody> execPostSql(@Header("Authorization") String token,@Body JSONObject req);
    @GET("web_query/")
    Observable<ResponseBody> execGetSql( @Header("Authorization") String token,@QueryMap JSONObject req);


    @POST("web_query/query/")
    Observable<ResponseBody> querySql(@Header("Authorization") String token,@Body JSONObject req);

    /**
     * 异步调用方法
     * @param req
     * @return
     */
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>...

    @POST("pb_workcenter/")
    Callback<ResultTokenBean> getAsyncToken(@Body JSONObject req, @Header("Authorization") String token);

    @POST("mq/{exchange}/{key}/")
    Callback<ResponseBody> sendAsyncMQmsg(@Path("exchange") String exchange,@Path("key") String key,@Body String msg,@Header("Authorization") String token);

    @POST("web_query/")
    Callback<ResponseBody> execAsyncPostcSql(@Body ParamExecSqlProcedureBean paramExecSqlBean, @Header("Authorization") String token);

    @GET("web_query/")
    Callback<ResponseBody> execAsyncGetcSql(@Body ParamExecSqlProcedureBean paramExecSqlBean, @Header("Authorization") String token);


    @POST("mq/{exchange}/{key}/")
    Observable<ResponseBody> getAsynMQmsg(@Path("exchange") String exchange,@Path("key") String key,@Body String msg,@Header("Authorization") String token);
}
