package cn.gyw.middleware.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class KafkaApplication {

    private final Logger logger = LoggerFactory.getLogger(KafkaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }

    @Autowired
    private KafkaTemplate<Object, Object> template;

    @Autowired
    private ReplyingKafkaTemplate replyingKafkaTemplate;

    @GetMapping("/send/{input}")
    public void sendFoo(@PathVariable String input) {
        this.template.send("topic_input", input);
    }

	/**
	 * 消息发送，等待回复
	 * @param input
	 * @throws Exception
	 */
	@GetMapping("/send/{input}")
	@Transactional(rollbackFor = RuntimeException.class)
	public void sendAndReceive(@PathVariable String input) throws Exception {
		ProducerRecord<String, String> record = new ProducerRecord<>("topic-kl", input);
		RequestReplyFuture<String, String, String> replyFuture = replyingKafkaTemplate.sendAndReceive(record);
		ConsumerRecord<String, String> consumerRecord = replyFuture.get();
		System.err.println("Return value: " + consumerRecord.value());
	}

	/**
	 * 事务消息
	 * @param input
	 */
	@GetMapping("/send/{input}")
	public void sendByTx(@PathVariable String input) {
		template.executeInTransaction(t ->{
			t.send("topic_input","kl");
			if("error".equals(input)){
				throw new RuntimeException("failed");
			}
			t.send("topic_input","ckl");
			return true;
		});
	}

    /**
     * 基本消费者
     *
     * @param input
     */
    @KafkaListener(id = "webGroup", topics = "topic_input")
    public void listen(String input) {
        logger.info("input value: {}", input);
    }

    /**
     * 消费指定topic
     * 显示的指定消费哪些Topic和分区的消息，
     * 设置每个Topic以及分区初始化的偏移量，
     * 设置消费线程并发度
     * 设置消息异常处理器
     *
     * @param input
     * @return
     */
    @KafkaListener(id = "webGroup", topicPartitions = {
            @TopicPartition(topic = "topic1", partitions = {"0", "1"}),
            @TopicPartition(topic = "topic2", partitions = "0",
                    partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "100"))
    }, concurrency = "6", errorHandler = "myErrorHandler")
    public String listenSpecialTopic(String input) {
        logger.info("input value: {}", input);
        return "successful";
    }

    /**
     * 消费者手动ack
     */
    @KafkaListener(id = "webGroup", topics = "topic-kl")
    public String listen(String input, Acknowledgment ack) {
        logger.info("input value: {}", input);
        if ("kl".equals(input)) {
            ack.acknowledge();
        }
        return "successful";
    }
}
