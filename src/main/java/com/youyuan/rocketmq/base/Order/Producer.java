package com.youyuan.rocketmq.base.Order;

import cn.hutool.db.sql.Order;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

/**
 * 类名称：Producer <br>
 * 类描述： 生产者 <br>
 *
 * @author zhangyu
 * @version 1.0.0
 * @date 创建时间：2020/6/26 13:48<br>
 */
public class Producer {

    //生产者组
    private static final String GROUP_NAME = "group1";
    //nameserver
    private static final String NAME_SERVER = "192.168.1.18:9876;192.168.1.19:9876;192.168.1.20:9876;192.168.1.21:9876";
    //主题名称
    private static final String TOPIC_NAME = "order_msg";
    //tag
    private static final String TAG = "order";

    public static void main(String[] args) throws MQClientException {
        //创建生产者
        DefaultMQProducer producer = new DefaultMQProducer(GROUP_NAME);
        //设置nameserver
        producer.setNamesrvAddr(NAME_SERVER);
        //启动
        producer.start();
        //构建订单数据
        List<OrderMsgBean> builder = OrderFactory.builder();
        builder.forEach((OrderMsgBean order) -> {
            //创建消息实体
            Message message = new Message(TOPIC_NAME, TAG, order.toString().getBytes());
            //发送消息
            try {
                /**
                 * 方法名: send  <br>
                 * 方法描述: 发送消息 <br>
                 *
                 * 默认情况下一个topic下有4个队列，可以从配置文件修改
                 * 第一个参数：消息
                 * 第二个参数：消息队列选择器(因为要发送顺序消息，所以需要根据orderId选择消息队列，相同orderId的消息发送到一个队列，才能保证消息顺序)
                 * 第三个参数：选择队列的属性,此场景是根据orderId来选择
                 * @return {@link sendResult 发送消息结果}
                 * @date 创建时间: 2020/6/26 13:56 <br>
                 * @author zhangyu
                 */
                SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                        //用orderId对队列列表取模来执行相同的orderId发送到同一个队列
                        long id = (Long) o;
                        long index = id % list.size();
                        return list.get((int) index);
                    }
                }, order.getOrderId());
                System.out.println("消息发送内容:" + order + "消息发送结果 : " + sendResult);
            } catch (MQClientException e) {
                e.printStackTrace();
            } catch (RemotingException e) {
                e.printStackTrace();
            } catch (MQBrokerException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        //关闭生产者
        producer.shutdown();
    }

}
