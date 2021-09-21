package cn.gyw.middleware.rocketmq.transaction;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

public class TransactionConsumer {

    private String groupName = "transaction-consumer";

    public static void main(String[] args) throws MQClientException {
        TransactionConsumer consumer = new TransactionConsumer();

        System.out.println("TransactionConsumer Started.");
    }

    public TransactionConsumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setNamesrvAddr("192.168.1.181:9876;192.168.1.182:9876");
        try {
            consumer.subscribe("TopicTransaction", "*");
            // 广播模式下需要先启动Consumer（广播：都发；集群模式：负载均衡）
            consumer.setMessageModel(MessageModel.BROADCASTING);

            consumer.registerMessageListener(new Listener());
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    private class Listener implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                        ConsumeConcurrentlyContext context) {
            System.out.println("批量拉取消息数量：" + msgs.size());
            try {
                for (MessageExt msg : msgs) {
                    String topic = msg.getTopic();
                    String msgBody = new String(msg.getBody(), "utf-8");
                    String tags = msg.getTags();
                    System.out.println("收到消息：Topic :" + topic + ",tags:" + tags + ", msg:" + msgBody);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER; // 异常重试
            }

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; // 发送成功
        }
    }
}
