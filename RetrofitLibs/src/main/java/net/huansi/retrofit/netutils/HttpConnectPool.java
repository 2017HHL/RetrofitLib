package net.huansi.retrofit.netutils;

import com.orhanobut.logger.Logger;

import net.huansi.retrofit.netapi.HttpApi;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

import static java.util.concurrent.TimeUnit.SECONDS;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BASIC;

/**
 * @date 创建时间 2019/3/27
 * @author qlzou
 * @Description  连接
 * @Version 1.0
 */
public class HttpConnectPool {
    private final int DEFAULT_CONNECT_TIMEOUT = 30;
    private final int DEFAULT_WRITE_TIMEOUT = 30;
    private final int DEFAULT_READ_TIMEOUT = 30;
    //已打开连接列表
    private Map<String,HttpApi> ipAPIMap;//key=>IP

    private static HttpConnectPool httpConnectPool;

    private HttpConnectPool(){
        ipAPIMap = new HashMap<>();
    }

    public static HttpConnectPool getInstance() {
        if(httpConnectPool==null) httpConnectPool=new HttpConnectPool();
        return httpConnectPool;
    }

    /**
     * 获取连接对象
     */
    public HttpApi getHttpApi(String ip){
        HttpApi httpApi=ipAPIMap.get(ip);
        if(httpApi==null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(createBuilder().build())
                    .addConverterFactory(FastJsonConverterFactory.create())//json转换成JavaBean
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("http://" + ip + "/")
                    .build();
            httpApi = retrofit.create(HttpApi.class);
            //添加到连接集合中
            ipAPIMap.put(ip,httpApi);
        }
       return httpApi;
    }

    /**
     * Retrofit 配置
     */
    private OkHttpClient.Builder createBuilder() {
        // 消息拦截器 可以看到接口返回的所有内容
        return new OkHttpClient
                .Builder()
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Logger.d(message);
                    }
                }).setLevel(BASIC))
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, SECONDS)
                .readTimeout(DEFAULT_WRITE_TIMEOUT, SECONDS)
                .writeTimeout(DEFAULT_READ_TIMEOUT, SECONDS)
                .retryOnConnectionFailure(true);
    }



}