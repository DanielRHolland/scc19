//thread example - Capturing fluctuating share price randomised by a thread

public class Shares {
    static int shareprice =0;

    public static void main(String[] args)
    {
        final int checks = 500; //number of share price readings
        final int delay = 2; //seconds between consecutive readings
        final int maxValue = 100; //maximum share value in the exchange
    
        SharesThread sth = new SharesThread(maxValue); //thread randomising share price
        sth.start();;
        for (int i = 0; i< checks; i++)
        {
            try {
                System.out.println("price = " + shareprice);
                Thread.sleep(1000*delay);
            }
            catch(java.lang.InterruptedException ie){};
        }
        
    }
}
