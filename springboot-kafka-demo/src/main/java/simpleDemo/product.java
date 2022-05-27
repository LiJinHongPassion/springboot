package simpleDemo;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Scanner;

/**
 * @author gp
 * @Date 2022/5/18
 **/
public class product {

    private static final String TOPIC="test";
    private static final String BROKER_LIST="124.223.45.196:9092";
    private static KafkaProducer<String,String> producer = null;

    static{
        Properties configs = initConfig();
        producer = new KafkaProducer<String, String>(configs);
    }

    private static Properties initConfig(){
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,BROKER_LIST);
//        这个参数用来指定分区中必须有多少个副本收到这条消息，之后生产者才会认为这条消息时写入成功
//        ack=0， 生产者在成功写入消息之前不会等待任何来自服务器的相应。如果出现问题生产者是感知
//        ack=1，默认值为1，只要集群的首领节点收到消息，生产这就会收到一个来自服务器的成功响
//        应。如果消息无法达到首领节点（比如首领节点崩溃，新的首领还没有被选举出来），生产者会收

//        acks=-1，生产者发送过来数据Leader和ISR队列里面所有Follwer应答，可靠性高，效率低；
        // 也可以写作 all
        properties.put(ProducerConfig.ACKS_CONFIG,"all");
        // 序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

//        重试机制
        properties.put(ProducerConfig.RETRIES_CONFIG,3);
//        重试间隔时间
        properties.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG,1000);
//        压缩,当前Kafka支持4种压缩方式，分别是gzip、snappy、lz4 及 zstd
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,"snappy");
//        消息缓冲区
//        发送到缓冲区中的消息会被分为一个一个的batch，分批次的发送到broker 端，这个参数就表示batch批次大小，默认值为16384，即16KB。因此减小batch大小有利于降低消息延时，增加batch大小有利于提升吞吐量。
//        通常合理调大该参数值，能够显著提升生产端吞吐量，比如可以调整到32KB，调大也意味着消息会有相对较大的延时。
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG,16384);
//        用来控制batch最大的空闲时间，超过该时间的batch也会被发送到broker端。这实际上是一种权衡，即吞吐量与延时之间的权衡。默认值为0，表示消息需要被立即发送，无需关系batch是否被填满。
//        通常为了减少请求次数、提升整体吞吐量，建议设置一个大于0的值，比如设置为100，此时会在负载低的情况下带来100ms的延时。
        // 设置长一点很容易发现问题
        properties.put(ProducerConfig.LINGER_MS_CONFIG,100000);
//        默认最长响应时间 30s
        properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,30000);


        return properties;
    }

    public static void main(String[] args){
        while(true) {
            try {
                Scanner in = new Scanner(System.in);
                String message =in.next() ;
                ProducerRecord<String, String> record = new ProducerRecord<String, String>(TOPIC, message);
                producer.send(record, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        if (null == exception) {
                            System.out.println("perfect!");
                        }
                        if (null != metadata) {
                            System.out.print("offset:" + metadata.offset() + ";partition:" + metadata.partition());
                        }
                    }
                }).get();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //	producer.close();
            }
        }
    }

}
