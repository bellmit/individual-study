package cn.gyw.middleware.rocketmq.quickstart;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * Consumer，订阅消息
 */
public class Consumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("quickstart-consumer");
        consumer.setNamesrvAddr("192.168.1.181:9876;192.168.1.182:9876");
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.subscribe("TopicQuickStart", "*");
        // 批量消费消息，默认为1条
        consumer.setConsumeMessageBatchMaxSize(10);

        // 批量拉取消息
//        consumer.setPullBatchSize(32);

        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
//                System.out.println(Thread.currentThread().getName() + " Receive New Messages: " + msgs);
                System.out.println("批量拉取消息数量：" + msgs.size());
                MessageExt msg = msgs.get(0);
                try {
//                    for (MessageExt msg : msgs) {
                        String topic = msg.getTopic();
                        String msgBody = new String(msg.getBody(), "utf-8");
                        String tags = msg.getTags();
                        System.out.println("收到消息：Topic :" + topic + ",tags:" + tags + ", msg:" + msgBody);

                        // 一定要先启动消费端，先订阅Topic
                        if ("Hello rocketMQ 4".equals(msgBody)) {
                            System.out.println("=======失败消息开始=========");
                            System.out.println("=======失败消息结束=========");
                            int a = 1 / 0; // 异常
                        }
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (msg.getReconsumeTimes() == 2) {
                        System.out.println("重试n 次后还是没有成功，记录日志，返回成功，不再重试");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER; // 异常重试
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; // 发送成功
            }
        });

        consumer.start();

        System.out.println("Consumer Started.");
    }

}
