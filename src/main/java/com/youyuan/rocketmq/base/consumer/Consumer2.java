package com.youyuan.rocketmq.base.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试消费模式是广播模式
 * @date 2020/5/25 22:54
 */
public class Consumer2 {
    //消费者组
    private static final String CONSUMER_GROUP_NAME = "consumer_group1";
    //nameserver地址
    private static final String NAME_SERVER_URL = "192.168.1.18:9876;192.168.1.19:9876;192.168.1.20:9876;192.168.1.21:9876";
    //指定消息的topic
    private static final String TOPIC_NAME = "base_topic";
    //指定消息的tag
    private static final String TAG_NAME = "base_tag_aysnc";

    public static void main(String[] args) throws MQClientException {
        //1、创建消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP_NAME);
        //2、指定nameserver地址
        consumer.setNamesrvAddr(NAME_SERVER_URL);
        //3、指定订阅主题和tag
        consumer.subscribe(TOPIC_NAME, TAG_NAME);
        //4、设置消费模式为负载均衡模式 设置为广播模式
        consumer.setMessageModel(MessageModel.BROADCASTING);
        //5、设置监听器用来监听mq
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                //接收消息
                list.forEach((msg) -> {
                    System.out.println("接收到转换后的消息体内容:" + new String(msg.getBody()));
                });
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //6、启动消费者监听
        consumer.start();
    }
}
