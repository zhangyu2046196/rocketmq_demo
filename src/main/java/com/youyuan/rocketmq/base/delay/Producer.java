package com.youyuan.rocketmq.base.delay;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.Random;

/**
 * 类名称：Producer <br>
 * 类描述： 测试延时消息生产者 <br>
 *
 * @author zhangyu
 * @version 1.0.0
 * @date 创建时间：2020/6/26 13:48<br>
 */
public class Producer {

    //生产者组
    private static final String GROUP_NAME = "producer_delay";
    //nameserver
    private static final String NAME_SERVER = "192.168.1.18:9876;192.168.1.19:9876;192.168.1.20:9876;192.168.1.21:9876";
    //主题名称
    private static final String TOPIC_NAME = "delay_msg";
    //tag
    private static final String TAG = "delay";

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        //创建生产者
        DefaultMQProducer producer = new DefaultMQProducer(GROUP_NAME);
        //设置nameserver
        producer.setNamesrvAddr(NAME_SERVER);
        //启动
        producer.start();
        for (int i = 1; i <= 10; i++) {
            int random = new Random().nextInt(5);
            if (random == 0) {
                random = 3;
            }
            //循环发送延时消息
            Message msg = new Message(TOPIC_NAME, TAG, ("延时消息:" + i + " 延迟级别:" + random).getBytes());
            //设置延时时间
            //延时消息的等级1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            //此处2代表延时5s
            msg.setDelayTimeLevel(random);
            SendResult sendResult = producer.send(msg);
            System.out.println("消息发送结果:" + sendResult);
        }
        //关闭生产者
        producer.shutdown();
    }

}
