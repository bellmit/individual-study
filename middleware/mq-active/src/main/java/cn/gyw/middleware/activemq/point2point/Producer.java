package cn.gyw.middleware.activemq.point2point;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * JMS helloworld：发送者
 */
public class Producer {

    // 1、建立ConnectionFactory 工厂对象
    private ConnectionFactory connectionFactory;
    // 2、通过ConnectionFactory 创建一个Connection连接
    private Connection connection;
    // 3、会话对象
    private Session session;
    // 4、生产者
    private MessageProducer messageProducer;

    public Producer() {
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

    public void sendMapMsg() {
        try {
            Destination desiDestination = session.createQueue("first");

            MapMessage mapMessage1 = session.createMapMessage();
            mapMessage1.setString("name", "z3");
            mapMessage1.setInt("age", 23);
            // 只有通过 set*Property 设置的属性才可以在Consumer中被过滤
            mapMessage1.setIntProperty("sal", 2300);
            mapMessage1.setStringProperty("color", "blue");

            MapMessage mapMessage2 = session.createMapMessage();
            mapMessage2.setString("name", "l4");
            mapMessage2.setInt("age", 26);
            mapMessage2.setIntProperty("sal", 2600);
            mapMessage2.setStringProperty("color", "red");

            MapMessage mapMessage3 = session.createMapMessage();
            mapMessage3.setString("name", "w5");
            mapMessage3.setInt("age", 28);
            mapMessage3.setIntProperty("sal", 5100);
            mapMessage3.setStringProperty("color", "gray");

            MapMessage mapMessage4 = session.createMapMessage();
            mapMessage4.setString("name", "gyw");
            mapMessage4.setInt("age", 30);
            mapMessage4.setIntProperty("sal", 4300);
            mapMessage4.setStringProperty("color", "blue");

            this.messageProducer.send(desiDestination, mapMessage1, DeliveryMode.PERSISTENT, 2, 1000 * 60 * 10);
            this.messageProducer.send(desiDestination, mapMessage2, DeliveryMode.PERSISTENT, 3, 1000 * 60 * 10);
            this.messageProducer.send(desiDestination, mapMessage3, DeliveryMode.PERSISTENT, 6, 1000 * 60 * 10);
            this.messageProducer.send(desiDestination, mapMessage4, DeliveryMode.PERSISTENT, 9, 1000 * 60 * 10);

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    public void sendTextMsg() {
        try {
            Destination desiDestination = session.createQueue("first");
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("这是一个测试文本内容");
            this.messageProducer.send(desiDestination, textMessage, DeliveryMode.NON_PERSISTENT, 9, 1000 * 60 * 10);
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

        Producer producer = new Producer();
//        producer.sendTextMsg();
        producer.sendMapMsg();

        producer.release();
    }
}
