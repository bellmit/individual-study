package cn.gyw.middleware.activemq.producer;

import cn.gyw.middleware.activemq.config.ActiveMQConfig;
import org.apache.activemq.*;

import javax.jms.*;
import java.util.concurrent.TimeUnit;

/**
 * 标准生产者
 */
public class CommonProducer {

    // activemq brokers
    // 第三种方式：?jms.useAsyncSend=true 异步消息
    private static final String BROKER_URL = ActiveMQConfig.build().get("activemq.brokerURL");
    // 用户名
    private static final String USER = ActiveMQConnectionFactory.DEFAULT_USER;
    // 密码
    private static final String PASSWORD = ActiveMQConnectionFactory.DEFAULT_PASSWORD;
    // 建立ConnectionFactory 工厂对象
    private ActiveMQConnectionFactory connectionFactory;
    // Connection连接
    private Connection connection;
    // 创建Session会话（上下文环境对象）
    private Session session;
    // 消息生产者
    private MessageProducer messageProducer;

    public CommonProducer() {
        init();
    }

    /**
     * 初始化方法
     */
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
        // 重试机制
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setMaximumRedeliveries(RedeliveryPolicy.DEFAULT_MAXIMUM_REDELIVERIES);
        // ... 其他配置省略
        connectionFactory.setRedeliveryPolicy(redeliveryPolicy);

        // 同步发送消息：非事务且持久消息，其他都为异步消息;
        // 异步发送不会等待broker的确认， 所以就需要考虑流量控制
        // producer每发送一个消息，统计一下发送的字节数，当字节数达到ProducerWindowSize值时，需要等待broker的确认，才能继续发送。
        connectionFactory.setProducerWindowSize(ActiveMQConnectionFactory.DEFAULT_PRODUCER_WINDOW_SIZE);
        // 第一种方式设置：发送异步消息
        connectionFactory.setUseAsyncSend(true);
    }

    /**
     * 发送消息
     */
    public void sendMessage() {
        try {
            // 2、通过ConnectionFactory 创建一个Connection连接，并且调用start方法开启连接，连接默认是关闭的。
            connection = connectionFactory.createConnection();
            // 第二种方式设置：异步消息
            ((ActiveMQConnection) connection).setUseAsyncSend(true);
            connection.start();

            // 3、通过Connection对象创建Session会话（上下文环境对象），用于接收消息，参数1：是否开启事务；参数2：签收模式，一般设置为自动签收。
            // Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

            // 以事务的方式执行，必须手动提交
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            // 客户端手动调用消息签收
            // session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);

            // 4、通过Session创建Destination对象，指定生产消息目标和消费消息来源的对象，
            // 在PTP模式中，Destination为Queue即队列，在Pub/Sub模式，Destination为Topic即主题。
            // 在程序中可以使用多个Queue和Topic。
//         Destination desiDestination = session.createQueue("my_queue");
            // 配置文件中配置优先级消费队列
            Destination desiDestination = session.createQueue("priority-queue");
            // 独占式消费
            //Destination desiDestination = session.createQueue("");

            // 不指定队列，通过send 方法来指定
//        Destination desiDestination = session.createQueue(null);

            // 5、通过Session对象创建消息和接收消息（生产者和消费者）：MessageProducer、MessageConsumer。
            messageProducer = session.createProducer(desiDestination);

            // 6、使用MessageProducer的setDeliveryMode方法为其设置持久化特性和非持久化特性。
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // 7、使用JMS 规范消息TextMessage 文本消息创建数据，并用MessageProducer 的send方法发送消息；
            // MessageConsumer 的receive方法接收数据。
            for (int i = 0; i < 5; i++) {
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText("id 为[" + i + "] 的消息....");
                // messageProducer.send(textMessage);
            /*
            第一个参数：目的地
            第二个参数：消息
            第三个参数：是否持久化
            第四个参数：优先级（0-9， 0-4 标识普通，5-9 表示加急，默认为4）优先级和顺序没什么必然联系
            第五个参数：消息在MQ上存放有效期，单位毫秒
            */
                // messageProducer.send(desiDestination, textMessage, DeliveryMode.NON_PERSISTENT, i, 1000 * 60 * 10);

                // 异步消息，没有收到确认消息，可能导致消息丢失;AMQ 提供了异常回调
                ActiveMQMessageProducer producer = (ActiveMQMessageProducer) messageProducer;
                producer.send(desiDestination, textMessage, DeliveryMode.NON_PERSISTENT, i, 1000 * 60 * 10,
                        new AsyncCallback() {
                            @Override
                            public void onSuccess() {
                                System.out.println("异步消息成功");
                            }

                            @Override
                            public void onException(JMSException e) {
                                System.out.println("异步消息异常");
                            }
                        });
                System.out.println("已发送消息数：" + (i + 1));
                TimeUnit.SECONDS.sleep(30);
            }

            // 使用事务提交
            session.commit();

            // 8、关闭Connection连接。
            if (connection != null) {
                connection.close();
            }
        } catch (JMSException e) {
            e.printStackTrace();
            // 异常消息回滚
            if (session != null) {
                try {
                    session.rollback();
                } catch (JMSException e1) {
                    e1.printStackTrace();
                }
            }
            // 异常重试机制
            System.out.println("发送消息失败，生产者做重发处理");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
