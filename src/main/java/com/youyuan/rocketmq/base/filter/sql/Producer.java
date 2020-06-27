package com.youyuan.rocketmq.base.filter.sql;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：Producer <br>
 * 类描述： 测试过滤消息生产者 sql表达式过滤 <br>
 *
 * @author zhangyu
 * @version 1.0.0
 * @date 创建时间：2020/6/26 13:48<br>
 */
public class Producer {

    //生产者组
    private static final String GROUP_NAME = "producer_sql";
    //nameserver
    private static final String NAME_SERVER = "192.168.1.18:9876;192.168.1.19:9876;192.168.1.20:9876;192.168.1.21:9876";
    //主题名称
    private static final String TOPIC_NAME = "sql_msg";
    //tag
    private static final String TAG = "tag1";

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        //创建生产者
        DefaultMQProducer producer = new DefaultMQProducer(GROUP_NAME);
        //设置nameserver
        producer.setNamesrvAddr(NAME_SERVER);
        //启动
        producer.start();
        //存储批量消息集合
        for (int i = 1; i <= 10; i++) {
            Message message = new Message(TOPIC_NAME, TAG, ("sql过滤消息:" + i).getBytes());
            //设置过滤属性 用于消费者通过sql表达式的方式过滤消息
            message.putUserProperty("i", String.valueOf(i));
            //发送消息
            SendResult sendResult = producer.send(message);
            System.out.println("sql过滤消息发送结果:" + sendResult);
        }
        //关闭生产者
        producer.shutdown();
    }

}
