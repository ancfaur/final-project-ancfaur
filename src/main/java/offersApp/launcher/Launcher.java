package offersApp.launcher;
import offersApp.config.storage.StorageProperties;
import offersApp.service.imageStorage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*
@SpringBootApplication
*/
@EntityScan(basePackages ={"offersApp.entity"})
@SpringBootApplication(scanBasePackages = {"offersApp.launcher","offersApp.bootstrap", "offersApp.entity", "offersApp.dto", "offersApp.repository", "offersApp.service", "offersApp.controller", "offersApp.converter", "offersApp.config", "offersApp.constants"})
@EnableJpaRepositories(basePackages = {"offersApp.repository"})
@EnableConfigurationProperties({StorageProperties.class})
@EnableTransactionManagement
public class Launcher  {
    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            //storageService.deleteAll();
            storageService.init();
        };
    }
}