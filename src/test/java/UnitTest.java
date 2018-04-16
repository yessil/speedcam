import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import speedcam.CameraEvent;
import speedcam.Database;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.ResourceBundle;

public class UnitTest {

    private static void dbtest() throws Exception{

        String url = null;
        ResourceBundle rb = ResourceBundle.getBundle("app");
        url=rb.getString("db");
        String query=rb.getString("query");

        Connection conn = DriverManager.getConnection(url);
        Statement stmt= conn.createStatement();
        try {
            stmt.execute(query);
        }catch (Exception ex){
            System.out.println(ex.getMessage());

        }
        ResultSet rs = stmt.getResultSet();
        int cnt =1;
        while (rs.next()){
            System.out.println(String.format("N: %d, Id:%d, Name: %s",cnt++, rs.getInt(1),rs.getString(2)));
        }
        if (cnt==1){
            System.out.println("No records found");
            return;
        }

    }

    private static void dbtest2(){

        Database db = new Database();
        db.storeToDb(new CameraEvent());
    }

    public static void main(String[] args){

        try {
            //dbtest2();
            objectMappingTest();

        } catch (Exception ex){

        }

    }

    public static void objectMappingTest(){

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        CameraEvent ce = new CameraEvent(1,1, 333, LocalDateTime.now(), 120, "aaa");
        String content = gson.toJson(ce);
        CameraEvent clone= gson.fromJson(content, CameraEvent.class);
        System.out.println(content);

    }
}
