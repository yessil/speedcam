package speedcam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class Database {

    private Connection conn = null;
    private String url = null;
    private Statement stmt = null;
    private Properties props =null;

    public Database(){

        props = Util.loadProperties("server.properties");
        url = props.getProperty("db");
        initDb(true);

    }

    private void initDb(boolean tableExists) {

        String table =  props.getProperty("checkTable");
        String sql = "select count(*) from "+table;
        try {

            if (conn == null){
                conn = DriverManager.getConnection(url);
                stmt = conn.createStatement();
                stmt.execute(sql);
            } else {
                if(!tableExists){
                    System.out.println("Creating the one...");
                    sql = props.getProperty("createTable");
                    stmt.executeUpdate(sql);
                }
            }

        } catch (Exception ex){
            String errorMessage=ex.getMessage();
            System.out.println(errorMessage);
            if (errorMessage.matches(".+(no such table: "+table+").+")){
                initDb(false);
            }

        }

    }

    public void storeToDb(CameraEvent ce){

        String sql = String.format(props.getProperty("insertTable"),ce.x, ce.y, ce.speed, ce.time, ce.speedLimit, ce.plateNumber);
        try {
            stmt.executeUpdate(sql);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }


}
