import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HelloSpringBean {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringBeanHelloWorld.class);
        MyService myService = ctx.getBean(MyService.class);
        System.out.println(myService.addFive(5));
    }
}
