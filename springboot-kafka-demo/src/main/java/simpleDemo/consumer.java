package simpleDemo;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
    //存储偏移量
    private static Map<TopicPartition, OffsetAndMetadata> offsetAndMetadataMap=new HashMap<>();
    static {
        Properties properties = initConfig();
        kafkaConsumer = new KafkaConsumer<String, String>(properties);
        // 订阅topic
        kafkaConsumer.subscribe(Arrays.asList(TOPIC));
    }

    private static Properties initConfig(){
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,BROKER_LIST);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"test3");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        // 设置手动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);

        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        // 设置自动提交
       //properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,true);
        //设置自动提交时间 一秒
//        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,1000);
        return properties;
    }

    public static void main(String[] args){
        try{
            while(true){
                // 设置手动提交以后 ，根据poll的频率提交offset
                ConsumerRecords<String,String> records = kafkaConsumer.poll(100);
                for(ConsumerRecord record:records){
                    try{
                        System.out.println(record.value());
                        System.out.println(record.offset());
                        System.out.println();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                if (records.count()>0){
                    // 异步提交
                    kafkaConsumer.commitAsync();
//                    扩展
                     kafkaConsumer.commitAsync((offsets,  exception)->{
                        if (exception!=null){
                            // 提交成功
                            System.out.println("提交成功");
                        }
                        else {
                            // 提交失败
                        }
                     });
                    // 同步提交
//                    kafkaConsumer.commitSync();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            kafkaConsumer.close();
        }
    }

}
