package com.youyuan.rocketmq.base.filter.tag;

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
 * 类描述： 测试过滤消息生产者 tag过滤 <br>
 *
 * @author zhangyu
 * @version 1.0.0
 * @date 创建时间：2020/6/26 13:48<br>
 */
public class Producer {

    //生产者组
    private static final String GROUP_NAME = "producer_tag";
    //nameserver
    private static final String NAME_SERVER = "192.168.1.18:9876;192.168.1.19:9876;192.168.1.20:9876;192.168.1.21:9876";
    //主题名称
    private static final String TOPIC_NAME = "tag_msg";
    //tag
    private static final String TAG = "tag2";

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        //创建生产者
        DefaultMQProducer producer = new DefaultMQProducer(GROUP_NAME);
        //设置nameserver
        producer.setNamesrvAddr(NAME_SERVER);
        //启动
        producer.start();
        //存储批量消息集合
        List<Message> messages = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            messages.add(new Message(TOPIC_NAME, TAG, ("批量消息:" + i).getBytes()));
        }
        //发送消息
        SendResult sendResult = producer.send(messages);
        System.out.println("批量消息发送结果:" + sendResult);
        //关闭生产者
        producer.shutdown();
    }

}
