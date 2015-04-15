package vote;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * Created by ashish on 4/14/2015.
 */
public class SimpleProducer {
    private static Producer<Integer, String> producer;
    private final Properties properties = new Properties();

    public SimpleProducer(){
        properties.put("metadata.broker.list", "54.149.84.25:9092");
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        properties.put("request.required.acks", "1");
        producer = new Producer<>(new ProducerConfig(properties));
    }


    public void sendEmail(String mod_email,String poll_result){

        new SimpleProducer();
        String topic = "cmpe273-topic";
        mod_email = "eashishbende@gmail.com";
        String msg = mod_email+":010052224:" + poll_result;
        System.out.println(msg);
        KeyedMessage<Integer, String> data = new KeyedMessage<>(topic, msg);
        producer.send(data);
        producer.close();
    }

}
