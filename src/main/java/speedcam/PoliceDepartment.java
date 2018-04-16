package speedcam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;

public class PoliceDepartment {


    private KafkaConsumer<String, String> consumer;
    private Gson gson;
    private Database db;

    public PoliceDepartment(){

        Properties props = Util.loadProperties("server.properties");
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(props.getProperty("topic")));
        gson = new GsonBuilder().create();
        db = new Database();

    }


    public void monitor() throws Exception{

        String content= null;
        CameraEvent ce = null;

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                content = record.value();
                System.out.printf("offset = %d content= %s%n", record.offset(), content);
                ce = gson.fromJson(content, CameraEvent.class);
                if (ce.speed > ce.speedLimit){
                    System.out.println("Speed limit breach detected !");
                    storeToDb(ce);

                }


            }
        }

    }

    private void storeToDb(CameraEvent ce) throws Exception{
        db.storeToDb(ce);
    }


}
