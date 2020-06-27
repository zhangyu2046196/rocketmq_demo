package com.youyuan.rocketmq.base.batch;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 类名称：ListSplitter <br>
 * 类描述：批量消息消息列表分割 <br>
 *
 * @author zhangyu
 * @version 1.0.0
 * @date 创建时间：2020/6/26 15:49<br>
 */
public class ListSplitter implements Iterator<List<Message>> {
    private final int SIZE_LIMIT = 1024 * 1024 * 4;
    private final List<Message> messages;
    private int currIndex;

    public ListSplitter(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean hasNext() {
        return currIndex < messages.size();
    }

    @Override
    public List<Message> next() {
        int nextIndex = currIndex;
        int totalSize = 0;
        for (; nextIndex < messages.size(); nextIndex++) {
            Message message = messages.get(nextIndex);
            int tmpSize = message.getTopic().length() + message.getBody().length;
            Map<String, String> properties = message.getProperties();
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                tmpSize += entry.getKey().length() + entry.getValue().length();
            }
            tmpSize = tmpSize + 20; // 增加日志的开销20字节
            if (tmpSize > SIZE_LIMIT) {
                //单个消息超过了最大的限制
                //忽略,否则会阻塞分裂的进程
                if (nextIndex - currIndex == 0) {
                    //假如下一个子列表没有元素,则添加这个子列表然后退出循环,否则只是退出循环
                    nextIndex++;
                }
                break;
            }
            if (tmpSize + totalSize > SIZE_LIMIT) {
                break;
            } else {
                totalSize += tmpSize;
            }

        }
        List<Message> subList = messages.subList(currIndex, nextIndex);
        currIndex = nextIndex;
        return subList;
    }

    public static void main(String[] args) {
        List<Message> messages = null;
        DefaultMQProducer producer = null;
        //把大的消息分裂成若干个小的消息
        ListSplitter splitter = new ListSplitter(messages);
        while (splitter.hasNext()) {
            try {
                List<Message> listItem = splitter.next();
                producer.send(listItem);
            } catch (Exception e) {
                e.printStackTrace();
                //处理error
            }
        }
    }
}
