package com.youyuan.rocketmq.base.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.body.ConsumerConnection;

import java.util.List;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试消费基本流程
 * @date 2020/5/24 22:24
 */
public class Consumer {

    //消费者组
    private static final String CONSUMER_GROUP_NAME = "consumer_group1";
    //nameserver地址
    private static final String NAME_SERVER_URL = "192.168.1.18:9876;192.168.1.19:9876;192.168.1.20:9876;192.168.1.21:9876";
    //指定消息的topic
    private static final String TOPIC_NAME = "base_topic";
    //指定消息的tag
    private static final String TAG_NAME = "base_tag_aysnc";

    public static void main(String[] args) throws Exception {
        //1、创建消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP_NAME);
        //2、设置nameserver地址
        consumer.setNamesrvAddr(NAME_SERVER_URL);
        //3、设置订阅的topic tag
        consumer.subscribe(TOPIC_NAME, "*");
        //4、设置监听用于接收生产者发的消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                //list为接收到的消息集合,因为发送的时候是字节数组所以接收的也是字节数组
                list.forEach((msg) -> {
                    System.out.println("消息全部内容:" + msg);
                    System.out.println("消息体:" + new String(msg.getBody()));
                });
                //返回消息后的状态
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //5、启动消费者
        consumer.start();
    }

}


