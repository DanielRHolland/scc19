package client;

import bl.ResolveIpBl;
import com.cdyne.ws.IP2Geo;
import com.cdyne.ws.IPInformation;
import com.cdyne.ws.ResolveIP;
import example.HelloWorldService;
import ui.IpResolver;


public class MyClient {

    public static void main(String[] args) {
//        System.out.println( new HelloWorldService().getHelloWorldPort().sayHelloWorldFrom("Dan"));
     //   IPInformation ipInfo = new IP2Geo().getIP2GeoSoap().resolveIP("81.110.242.159 ","");
       // System.out.println("City "+ipInfo.getCity());
        System.out.println(new ResolveIpBl().getCity("81.110.242.159"));
        new IpResolver().showForm();
    }

}
