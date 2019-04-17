package net.huansi.retrofit.netmanage;
import com.alibaba.fastjson.JSONObject;

import net.huansi.retrofit.bean.ParamTokenBean;
import net.huansi.retrofit.bean.ResultTokenBean;
import net.huansi.retrofit.netapi.HttpApi;
import net.huansi.retrofit.utils.DefaultObserver;
import net.huansi.retrofit.utils.RxUtil;
import net.huansi.retrofit.listener.WebResponseListener;

/**
 * Auth验证管理类
 */
public class AuthManager extends BaseManager{

    public AuthManager(HttpApi httpApi) {
        super(httpApi);
    }

    /**
     * 获取Token
     */
    public  void getToken(ParamTokenBean bean, final WebResponseListener<ResultTokenBean> webResponseListener) {
        if(httpApi==null) return;
        if(bean==null) return;
        JSONObject req =new JSONObject();
        req.put("password",bean.getPassWord());
        req.put("username", bean.getUserName());
        httpApi.getToken(req)
                .compose(RxUtil.<ResultTokenBean>rxSchedulerHelper())
                .subscribe(new DefaultObserver<ResultTokenBean>(webResponseListener) {
                    @Override
                    public void onSuccess(ResultTokenBean resultTokenBean) {
                        if (webResponseListener !=null) webResponseListener.success(resultTokenBean);
                    }
                });
    }
}
