package com.youyuan.rocketmq.base.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试发送异步消息
 * @date 2020/4/25 21:18
 */
public class ASyncProducer {

    //生产者组
    private static final String PRODUCER_GROUP_NAME = "producer_group1";
    //nameserver地址
    private static final String NAME_SERVER_URL = "192.168.1.18:9876;192.168.1.19:9876;192.168.1.20:9876;192.168.1.21:9876";
    //指定消息的topic
    private static final String TOPIC_NAME = "base_topic";
    //指定消息的tag
    private static final String TAG_NAME = "base_tag_aysnc";

    public static void main(String[] args) throws Exception {
        //1、创建Producer消息生产者
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP_NAME);
        //2、生产者连接NameServer
        producer.setNamesrvAddr(NAME_SERVER_URL);
        //3、启动生产者
        producer.start();
        //4、发送消息
        for (int i = 1; i <= 10; i++) {
            //创建消息
            Message msg = new Message(TOPIC_NAME, TAG_NAME, ("hello world base msg test" + i).getBytes());
            //异步发送消息
            producer.send(msg, new SendCallback() {
                /**
                 * 发送成功
                 * @param sendResult
                 */
                public void onSuccess(SendResult sendResult) {
                    System.out.println("消息发送成功," + sendResult);
                }

                /**
                 * 发送异常
                 * @param throwable
                 */
                public void onException(Throwable throwable) {
                    System.out.println("消息发送异常," + throwable);
                }
            });
        }
        // 5、关闭生产者
        //producer.shutdown();
    }

}
