package example.client;

import example.wservice.HelloWorldService;

//To generate classes from WSDL, use:
//wsimport -p example.wservice -d out/production/WebClientLab8/ -s src http://localhost:9000/HelloWorld?wsdl
public class MyClient {
    public static void main(String[] args) {
        HelloWorldService helloWorldService = new HelloWorldService();
        String reply = helloWorldService.getHelloWorldPort().sayHelloWorldFrom("Dan");
        System.out.println(reply);
    }
}
