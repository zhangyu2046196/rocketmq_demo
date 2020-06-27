package com.youyuan.rocketmq.base.Order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 类名称：Consumer <br>
 * 类描述： 消费者 <br>
 *
 * @author zhangyu
 * @version 1.0.0
 * @date 创建时间：2020/6/26 14:04<br>
 */
public class Consumer {

    //生产者组
    private static final String GROUP_NAME = "order_consumer";
    //nameserver
    private static final String NAME_SERVER = "192.168.1.18:9876;192.168.1.19:9876;192.168.1.20:9876;192.168.1.21:9876";
    //主题名称
    private static final String TOPIC_NAME = "order_msg";
    //tag
    private static final String TAG = "order";

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
        //因为要顺序消费队列数据，所以需要保证每个队列只能有一个线程消费，所以使用MessageListenerOrderly
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                list.forEach((msg) -> System.out.println("【" + Thread.currentThread().getName() + "】消息内容：" + new String(msg.getBody())));
                consumeOrderlyContext.setAutoCommit(Boolean.TRUE);
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        //启动消费者
        consumer.start();
    }
}
