package net.huansi.retrofit.netmanage;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import net.huansi.retrofit.bean.ConfigReceiveMQBean;

import java.io.IOException;

/**
 * MQ消息管理类 接收
 */
public class MQReceiveManager{
    public interface  MQReceiveListener{
        void receiveMsg(String msg);
    }

    private ConfigReceiveMQBean receiveMQBean;
    private MQReceiveListener mqReceiveListener;
    private ConnectionFactory factory;// 声明ConnectionFactory对象

    public MQReceiveManager(ConfigReceiveMQBean receiveMQBean,MQReceiveListener mqReceiveListener){
        this.receiveMQBean=receiveMQBean;
        this.mqReceiveListener=mqReceiveListener;

        factory= new ConnectionFactory();
        factory.setHost(receiveMQBean.getIp());//主机地址：192.168.1.105
        factory.setPort(receiveMQBean.getPort());// 端口号:5672
        factory.setUsername(receiveMQBean.getUserName());// 用户名
        factory.setPassword(receiveMQBean.getPassWord());// 密码
        factory.setAutomaticRecoveryEnabled(true);// 设置连接恢复
    }

    public void startMQ(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection connection = factory.newConnection();
                    Channel channel = connection.createChannel();
                    // 处理完一个消息，再接收下一个消息
                    channel.basicQos(1);
                    // 随机命名一个队列名称
//                    String queueName = System.currentTimeMillis() + "queueNameCar";
                    // 声明交换机类型
                    channel.exchangeDeclare(receiveMQBean.getExchangeName(), receiveMQBean.getExchangeType(), true);
                    // 声明队列（持久的、非独占的、连接断开后队列会自动删除）
                    channel.queueDeclare(receiveMQBean.getQueue(), true, false, false, null);// 声明共享队列
                    // 根据路由键将队列绑定到交换机上（需要知道交换机名称和路由键名称）
                    channel.queueBind(receiveMQBean.getQueue(), receiveMQBean.getExchangeName(), receiveMQBean.getRoutingKey());
                    // 创建消费者获取rabbitMQ上的消息。每当获取到一条消息后，就会回调handleDelivery（）方法，该方法可以获取到消息数据并进行相应处理
                    //创建消费者
                    channel.basicConsume(receiveMQBean.getQueue(), true, new DefaultConsumer(channel){
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                            if(body==null) return;
                            if(mqReceiveListener!=null) mqReceiveListener.receiveMsg(new String(body));
                        }
                    });

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void stopMQ(){}
}
