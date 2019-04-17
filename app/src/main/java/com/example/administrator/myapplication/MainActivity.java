package com.example.administrator.myapplication;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import net.huansi.retrofit.bean.ConfigReceiveMQBean;
import net.huansi.retrofit.bean.ParamExecSqLBaseBean;
import net.huansi.retrofit.bean.ParamExecSqlPlaceholderBean;
import net.huansi.retrofit.bean.ParamTokenBean;
import net.huansi.retrofit.bean.ResultTokenBean;
import net.huansi.retrofit.netapi.HttpApi;
import net.huansi.retrofit.netmanage.AuthManager;
import net.huansi.retrofit.netmanage.ExecSqlManager;
import net.huansi.retrofit.netmanage.MQReceiveManager;
import net.huansi.retrofit.netutils.HttpConnectPool;
import net.huansi.retrofit.listener.WebResponseListener;

public class MainActivity extends AppCompatActivity {
    private ExecSqlManager sqlManage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1= findViewById(R.id.bt1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 /**
                 * 获取Token 传入ip示例
                 */
                HttpApi httpApi=HttpConnectPool.getInstance().getHttpApi("47.97.182.182:22101");
                AuthManager authManager =new AuthManager(httpApi);
                ParamTokenBean paramTokenBean=new ParamTokenBean();
                paramTokenBean.setUserName("huansi");
                paramTokenBean.setPassWord("huansi.net");
                authManager.getToken(paramTokenBean, new WebResponseListener<ResultTokenBean>() {
                    @Override
                    public void success(ResultTokenBean resultTokenBean) {

                    }

                    @Override
                    public void error(WebResponseError error) {

                    }
                });
            }
        });
        Button button2= findViewById(R.id.bt2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpApi httpApi = HttpConnectPool.getInstance().getHttpApi("47.110.145.204:8089");
                sqlManage=new ExecSqlManager(httpApi);
//                JSONObject jsonObject=new JSONObject();
//                jsonObject.put("sBillNo","sss");
//                jsonObject.put("sMachine","");
//                jsonObject.put("sUserNo","");
//                ParamExecSqlProcedureBean bean=new ParamExecSqlProcedureBean();
//                bean.setProcedureName("dbo.spppAccessoriesProcess_task_querylist");
//                bean.setParam(jsonObject);
//                bean.setQuery(true);
//                bean.setRequestType(ParamExecSqLBaseBean.RequestType.POST);

                ParamExecSqlPlaceholderBean bean=new ParamExecSqlPlaceholderBean();
                bean.setSql("EXEC dbo.spppAccessoriesProcess_task_querylist  @sUserNo='%1$s', @sMachine='%2$s', @sBillNo='%3$s'");
                bean.setQuery(true);
                bean.setRequestType(ParamExecSqLBaseBean.RequestType.POST);
                String[] arr={"","","sss"};
                bean.setPlaceholderArray(arr);
                sqlManage.execQuerySql(bean, new WebResponseListener<String>() {
                    @Override
                    public void success(String s) {

                    }

                    @Override
                    public void error(WebResponseError error) {

                    }
                });
            }
        });

        Button button3= findViewById(R.id.bt3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                HttpApi httpApi = HttpConnectPool.getInstance().getHttpApi("47.110.145.204:8089");
//                MQSendManager mqSendManager=new MQSendManager(httpApi);
//                ParamSendMQBean bean=new ParamSendMQBean();
//                ParamSendMQBean.ParamSendMQDataBean dataBean=new ParamSendMQBean.ParamSendMQDataBean();
//                dataBean.setData("2");
//                dataBean.setType("1");
//                bean.setExchangeName("huansi");
//                bean.setRoutingKey("huansi.tools.sql");
//                bean.setDataBean(dataBean);
//                mqSendManager.sendMQMsg(bean, new WebResponseListener<String>() {
//                    @Override
//                    public void success(String s) {
//
//                    }
//
//                    @Override
//                    public void error(WebResponseError error) {
//
//                    }
//                });
                ConfigReceiveMQBean bean=new ConfigReceiveMQBean();
                bean.setIp("106.13.72.179");
                bean.setPort(5672);
                bean.setUserName("shan");
                bean.setPassWord("shan");
                bean.setRoutingKey("test_name_routing");
                bean.setExchangeType("direct");
                bean.setExchangeName("test_name_exchange");
                bean.setQueue("test_name_queue");
                MQReceiveManager mqReceiveManager=new MQReceiveManager(bean, new MQReceiveManager.MQReceiveListener() {
                    @Override
                    public void receiveMsg(String msg) {

                    }
                });
                mqReceiveManager.startMQ();
            }
        });


    }
}
