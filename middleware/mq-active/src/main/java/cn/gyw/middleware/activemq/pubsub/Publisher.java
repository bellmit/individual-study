package cn.gyw.middleware.activemq.pubsub;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * JMS ：发布者
 */
public class Publisher {

    // 1、建立ConnectionFactory 工厂对象
    private ConnectionFactory connectionFactory;
    // 2、通过ConnectionFactory 创建一个Connection连接
    private Connection connection;
    // 3、会话对象
    private Session session;
    // 4、生产者
    private MessageProducer messageProducer;

    public Publisher() {
        try {
            this.connectionFactory = new ActiveMQConnectionFactory(
                    "gyw",
                    "gyw",
                    "tcp://localhost:61616"
            );
            this.connection = connectionFactory.createConnection();
            this.connection.start();
            this.session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
            this.messageProducer = session.createProducer(null);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendTextMsg() {
        try {
            Destination desiDestination = session.createTopic("topic-1");
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("这是一个测试文本内容");
            this.messageProducer.send(desiDestination, textMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public Session getSession() {
        return session;
    }

    public void release() {
        // 8、关闭Connection连接。
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws JMSException {

        Publisher publisher = new Publisher();
        publisher.sendTextMsg();
        publisher.release();
    }
}
