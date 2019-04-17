package net.huansi.retrofit.netmanage;
import net.huansi.retrofit.bean.ParamSendMQBean;
import net.huansi.retrofit.netapi.HttpApi;
import net.huansi.retrofit.utils.DefaultObserver;
import net.huansi.retrofit.utils.RxUtil;
import net.huansi.retrofit.listener.WebResponseListener;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * MQ消息管理类 发送
 */
public class MQSendManager extends BaseManager{
    public MQSendManager(HttpApi httpApi) {
        super(httpApi);
    }

    /**
     * 发送MQ消息
     */
    public void sendMQMsg(ParamSendMQBean bean, final WebResponseListener<String> webResponseListener) {
        httpApi.sendMQmsg(bean.getToken(),bean.getExchangeName(),bean.getRoutingKey(), bean.getDataBean())
                .compose(RxUtil.<ResponseBody>rxSchedulerHelper())
                .subscribe(new DefaultObserver<ResponseBody>(webResponseListener) {
                    @Override
                    public void onSuccess(ResponseBody response) {
                        String json= "";
                        try {
                            json = response.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (webResponseListener !=null)webResponseListener.success(json);
                    }
                });
    }

}
