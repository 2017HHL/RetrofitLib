package net.huansi.retrofit.bean;

/**
 * @date 创建时间 2019/3/27
 * @author qlzou
 * @Description  MQ的接收请求实体
 * @Version 1.0
 */
public class ParamSendMQBean extends ParamBaseBean {

    //交换机名称
    private String exchangeName;
    //路由key
    private String routingKey;

    private ParamSendMQDataBean dataBean;

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public ParamSendMQDataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(ParamSendMQDataBean dataBean) {
        this.dataBean = dataBean;
    }

    public static class ParamSendMQDataBean{
        private String type;//类型数据
        private String data;//数据

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

}
