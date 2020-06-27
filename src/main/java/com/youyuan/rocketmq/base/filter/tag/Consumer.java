package com.youyuan.rocketmq.base.filter.tag;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 类名称：Consumer <br>
 * 类描述： 测试过滤消息消费者 tag过滤 <br>
 *
 * @author zhangyu
 * @version 1.0.0
 * @date 创建时间：2020/6/26 14:04<br>
 */
public class Consumer {

    //生产者组
    private static final String GROUP_NAME = "consumer_tag";
    //nameserver
    private static final String NAME_SERVER = "192.168.1.18:9876;192.168.1.19:9876;192.168.1.20:9876;192.168.1.21:9876";
    //主题名称
    private static final String TOPIC_NAME = "tag_msg";
    //tag
    private static final String TAG = "tag";

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
        //订阅主题  指定tag过滤消息 多个可以用||拼接  如果过滤topic下所有的tag 可以使用*
        consumer.subscribe(TOPIC_NAME, "tag1 || tag2");
        //注册监听
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                list.forEach((msg) -> System.out.println("【" + Thread.currentThread().getName() + "】消息内容：" + new String(msg.getBody())));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动消费者
        consumer.start();
        System.out.println("消费者启动");
    }
}
