package speedcam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.management.timer.Timer;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;


public class EventGenerator {

    private Random random  = null;

    private Properties props = null;
    private int defaultSpeedLimit = 0;
    private String topic = null;
    private Gson gson = null;

    private Producer<String, String> producer = null;

   public EventGenerator() throws Exception{

       props = Util.loadProperties("client.properties");
       gson = new GsonBuilder().create();
       defaultSpeedLimit = Integer.parseInt(props.getProperty("defaultSpeedLimit"));
       topic = props.getProperty("topic");

       producer = new KafkaProducer<String, String>(props);

   }

   public void sendEvent(CameraEvent ce, String topic) throws IOException{

       //serialize camera event object to JSON formatted string
       String content = gson.toJson(ce);
       // send it to stream
       producer.send(new ProducerRecord<String, String>(topic, ce.id.toString(), content));

   }

    public void sendEvent() throws IOException{

        //serialize camera event object to JSON formatted string
        CameraEvent ce = createEvent();
        String content = gson.toJson(ce);
    //    System.out.println("gen: "+content);
        //CameraEvent clone = gson.fromJson(content, CameraEvent.class); //test
        // send it to stream
        producer.send(new ProducerRecord<String, String>(topic, ce.id.toString(), content));

    }

   public CameraEvent createEvent(){

       random = new Random(LocalDateTime.now().getNano());
       int x = random.nextInt(100);// some fictional city coordinate system: x, y coord are between 0 and 100
       int y = random.nextInt(100);
       int speed = random.nextInt(200); // not a lamborghini grade

       return new CameraEvent(x,y, speed,LocalDateTime.now(), generateSpeedLimit(x,y), generatePlateNumber());

   }

   private int generateSpeedLimit(int x, int y){

       // generally speedLimit depends on location, but in this demo it is a constant
       return defaultSpeedLimit;
   }


   private String generatePlateNumber(){
       // some random string
       return String.format("XYZ %d", random.nextInt(9999));
   }


}
