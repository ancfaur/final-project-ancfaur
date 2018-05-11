package offersApp.launcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*
@SpringBootApplication
*/
@EntityScan(basePackages ={"offersApp.entity"})
@SpringBootApplication(scanBasePackages = {"offersApp.launcher", "offersApp.entity", "offersApp.dto", "offersApp.repository", "offersApp.service", "offersApp.controller", "offersApp.converter", "offersApp.config", "offersApp.constants"})
@EnableJpaRepositories(basePackages = {"offersApp.repository"})
public class Launcher  {
    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
    }
}