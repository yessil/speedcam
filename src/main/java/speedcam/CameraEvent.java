package speedcam;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class CameraEvent {
    // location: some sort of rectangualr coordinate system on city map, might be GPS coordinates, etc.
    public final UUID id; //unique event id
    int x;
    int y;
    // recorded speed
    int speed;
    // recorded time
    LocalDateTime time;
    // speed limit at this location
    int speedLimit;
    // car's number
    String plateNumber;

    public CameraEvent(){
        id=UUID.randomUUID();
    }

    public  CameraEvent(int x, int y, int speed, LocalDateTime time, int speedLimit, String plateNumber){

        id = UUID.randomUUID();
        this.x=x;
        this.y=y;
        this.speed=speed;
        this.speedLimit=speedLimit;
        this.time=time;
        this.plateNumber=plateNumber;

    }

}
