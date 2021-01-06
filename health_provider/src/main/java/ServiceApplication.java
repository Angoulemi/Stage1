import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class ServiceApplication {
    public static void main(String[] args) throws IOException {
        new ClassPathXmlApplicationContext("classpath:applicationContext-service.xml");
        int read = System.in.read();
    }
}