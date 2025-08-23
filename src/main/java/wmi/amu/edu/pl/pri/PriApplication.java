package wmi.amu.edu.pl.pri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"wmi.amu.edu.pl.pri"})
public class PriApplication {

    public static void main(String[] args) {
        SpringApplication.run(PriApplication.class, args);
    }
}
