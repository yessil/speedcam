import speedcam.EventGenerator;
import speedcam.PoliceDepartment;

public class DemoTest {

    public static final int SOME_NUMBER = 1000;
    public static final int SOME_DELAY = 100;

    public static void main(String[] args) throws  Exception{

        startSpeedCams(); // could be a separate process
        setPoliceDepartmentOnDuty();

    }

    private static void setPoliceDepartmentOnDuty(){

        PoliceDepartment pd = new PoliceDepartment();
        try {
            pd.monitor();

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }


        System.out.println("Press Ctrl-C to stop PoliceDepartment");

    }

    private static void startSpeedCams(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EventGenerator eg = new EventGenerator();
                    for(int i=0; i<SOME_NUMBER; i++){
                        eg.sendEvent();
                        Thread.sleep(SOME_DELAY);
                    }

                }catch (Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        }).start();

    }
}
