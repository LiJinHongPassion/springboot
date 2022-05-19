package simpleDemo;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

/**
 * @author gp
 * @Date 2022/5/18
 * 😁😄
 **/
public class consumer {


    private static final String TOPIC="test";
    private static final String BROKER_LIST="124.223.45.196:9092";
    private static KafkaConsumer<String,String> kafkaConsumer = null;

    static {
        Properties properties = initConfig();
        kafkaConsumer = new KafkaConsumer<String, String>(properties);
        kafkaConsumer.subscribe(Arrays.asList(TOPIC));
    }

    private static Properties initConfig(){
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,BROKER_LIST);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"test1");
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG,"test");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);
        return properties;
    }

    public static void main(String[] args){
        try{
            while(true){
                ConsumerRecords<String,String> records = kafkaConsumer.poll(100);
                for(ConsumerRecord record:records){
                    try{
                        System.out.println(record.value());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            kafkaConsumer.close();
        }
    }

}
