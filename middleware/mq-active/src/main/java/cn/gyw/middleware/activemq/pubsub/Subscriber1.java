package cn.gyw.middleware.activemq.pubsub;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Subscriber1 {

    public final String SELECTOR_1 = "color = 'blue'";
    public final String SELECTOR_2 = "color = 'blue' and sal > 2000";
    public final String SELECTOR_3 = "receiver = 'A'";

    // 1、建立ConnectionFactory 工厂对象
    private ConnectionFactory connectionFactory;
    // 2、通过ConnectionFactory 创建一个Connection连接
    private Connection connection;
    // 3、会话对象
    private Session session;
    // 4、消费者
    private MessageConsumer messageConsumer;
    // 5、目标
    private Destination destination;

    public Subscriber1() {
        try {
            this.connectionFactory = new ActiveMQConnectionFactory(
                    "gyw",
                    "gyw",
                    "tcp://localhost:61616"
            );
            this.connection = connectionFactory.createConnection();
            this.connection.start();
            this.session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
            this.destination = session.createTopic("topic-1");
            // 创建消费者的时候发生了变化
            this.messageConsumer = session.createConsumer(this.destination);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void receiver() {
        try {
            this.messageConsumer.setMessageListener(new Listener());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public Session getSession() {
        return session;
    }

    // 非静态内部类
    class Listener implements MessageListener {

        @Override
        public void onMessage(Message message) {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                System.out.println(">>" + textMessage);
            }

            if (message instanceof MapMessage) {
                MapMessage mapMessage = (MapMessage) message;
                System.out.println(">>" + mapMessage);
            }
            try {
                message.acknowledge();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws JMSException {

        Subscriber1 consumer = new Subscriber1();
        consumer.receiver();
    }
}
