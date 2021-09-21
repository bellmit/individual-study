package cn.gyw.middleware.rocketmq.quickstart;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * 发送消息
 */
public class Producer {

    public static void main(String[] args) throws InterruptedException, MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("quickstart-producer");
        producer.setNamesrvAddr("192.168.1.181:9876;192.168.1.182:9876");

        producer.setCompressMsgBodyOverHowmuch(1024*1024*5);
        producer.setMaxMessageSize(1024*1024*20);

        producer.start();

        for (int i = 0 ; i < 10 ; i ++) {

            Message msg = new Message("TopicQuickStart",
                    "TagA",
                    ("Hello rocketMQ " + i).getBytes());
            SendResult sendResult = null;
            try {
                sendResult = producer.send(msg);
            } catch (MQClientException e) {
                TimeUnit.SECONDS.sleep(1);
                e.printStackTrace();
            } catch (RemotingException e) {
                e.printStackTrace();
            } catch (MQBrokerException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(sendResult);
        }

        producer.shutdown();
    }

}
