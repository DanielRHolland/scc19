
import java.util.Random;

public class SharesThread extends Thread
{
    
    public SharesThread(int maxValue) {
        this.maxValue = maxValue;
    }
    
    public  void fluctuatePrice()
    {
        while(true) {
            Random rn = new Random(); //random number generator
            Shares.shareprice = rn.nextInt(50); //set shareValue in main class
            try {
                this.sleep(frequency);
            } catch(java.lang.InterruptedException ie){}; //delay fluctuation*/
        }
    }
    
    public void run()
    {
        fluctuatePrice();
    }
    
    private int maxValue=100;
    private final long frequency=200; //frequency of fluctuating share value in msec
}
