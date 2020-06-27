package com.youyuan.rocketmq.base.delay;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 类名称：Consumer <br>
 * 类描述： 测试延时消息消费者 <br>
 *
 * @author zhangyu
 * @version 1.0.0
 * @date 创建时间：2020/6/26 14:04<br>
 */
public class Consumer {

    //生产者组
    private static final String GROUP_NAME = "consumer_delay";
    //nameserver
    private static final String NAME_SERVER = "192.168.1.18:9876;192.168.1.19:9876;192.168.1.20:9876;192.168.1.21:9876";
    //主题名称
    private static final String TOPIC_NAME = "delay_msg";
    //tag
    private static final String TAG = "delay";

    public static void main(String[] args) throws MQClientException {
        //创建消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(GROUP_NAME);
        //设置nameserver
        consumer.setNamesrvAddr(NAME_SERVER);
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //订阅主题
        consumer.subscribe(TOPIC_NAME, "*");
        //注册监听
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                list.forEach((msg) -> System.out.println("【" + Thread.currentThread().getName() + "】消息内容：" + new String(msg.getBody()) + " 延时时间:" + (System.currentTimeMillis() - msg.getStoreTimestamp())));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动消费者
        consumer.start();
        System.out.println("消费者启动");
    }
}
