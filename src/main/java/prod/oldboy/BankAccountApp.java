package prod.oldboy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import prod.oldboy.service.AmountService;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BankAccountApp {
    public static void main(String[] args) {

        ConfigurableApplicationContext context =
                SpringApplication.run(BankAccountApp.class, args);

        System.out.println("Start app...");

    }
}

