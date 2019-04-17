package net.huansi.retrofit.bean;

/**
 * Created by Administrator on 2019/3/18 0018.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 接收MQ返回的数据
 */
public class ResultSendMQBean extends ResultBaseBean{
    @JSONField(name = "sType") private String type;//类型数据
    @JSONField(name = "sData") private String data;//数据

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
