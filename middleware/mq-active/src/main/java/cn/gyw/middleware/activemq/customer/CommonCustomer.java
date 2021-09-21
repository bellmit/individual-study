package cn.gyw.middleware.activemq.customer;

import cn.gyw.middleware.activemq.config.ActiveMQConfig;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 标准消费者
 */
public class CommonCustomer {

    // activemq brokers
    private static final String BROKER_URL = ActiveMQConfig.build().get("activemq.brokerURL");
    // 用户名
    private static final String USER = ActiveMQConnectionFactory.DEFAULT_USER;
    // 密码
    private static final String PASSWORD = ActiveMQConnectionFactory.DEFAULT_PASSWORD;

    // 建立ConnectionFactory 工厂对象
    private ConnectionFactory connectionFactory;
    // Connection连接
    private Connection connection;
    // 创建Session会话（上下文环境对象）
    private Session session;
    // 消息消费者
    private MessageConsumer messageConsumer;

    public CommonCustomer() {
        init();
    }

    private void init() {
        // 1、建立ConnectionFactory 工厂对象，需要填入用户名、密码、以及要连接的地址，均使用默认即可，默认端口为“tcp://localhost:61616”，可以通过 config/activemq.xml修改。
        /*ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://localhost:61616"
        );*/
        // 安全机制
        connectionFactory = new ActiveMQConnectionFactory(
                USER,
                PASSWORD,
                BROKER_URL
        );
    }

    public void handleMessage() {
        try {

            // 2、通过ConnectionFactory 创建一个Connection连接，并且调用start方法开启连接，连接默认是关闭的。
            connection = connectionFactory.createConnection();
            connection.start();

            // 3、通过Connection对象创建Session会话（上下文环境对象），用于接收消息，参数1：是否开启事务；参数2：签收模式，一般设置为自动签收。
            // Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

            // 客户端手动确认签收消息，一般使用手动签收的方式
            session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);

            // 4、通过Session创建Destination对象，指定生产消息目标和消费消息来源的对象，
            // 在PTP模式中，Destination为Queue即队列，在Pub/Sub模式，Destination为Topic即主题。
            // 在程序中可以使用多个Queue和Topic。
            // Destination desiDestination = session.createQueue("my_queue");

            // 配置文件中配置优先级消费队列
            Destination desiDestination = session.createQueue("priority-queue");

            // 5、通过Session对象创建消息和接收消息（生产者和消费者）：MessageProducer、MessageConsumer。
            messageConsumer = session.createConsumer(desiDestination);

            // 7、使用JMS 规范消息TextMessage 文本消息创建数据，并用MessageProducer 的send方法发送消息；
            // MessageConsumer 的receive方法接收数据。
            while (true) {
                TextMessage textMessage = (TextMessage) messageConsumer.receive();
                textMessage.acknowledge();
                if (textMessage == null) {
                    break;
                }
                System.out.println("接收到的消息：" + textMessage.getText());
            }

            // 8、关闭Connection连接。
            if (connection != null) {
                connection.close();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
