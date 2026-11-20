package ua.edu.krnu.javatech.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.edu.krnu.javatech.model.EdgeNode;
import ua.edu.krnu.javatech.model.NodeStatus;
import ua.edu.krnu.javatech.repository.EdgeNodeRepository;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeData(EdgeNodeRepository repository) {
        return args -> {
            if (repository.count() != 0) {
                return;
            }
            repository.saveAll(List.of(
                    new EdgeNode("edge-node-01", "x86_64", 4, 16, 256, NodeStatus.ONLINE, "Site-A"),
                    new EdgeNode("edge-node-02", "ARM64", 8, 32, 512, NodeStatus.DEGRADED, "Site-B")
            ));
        };
    }
}