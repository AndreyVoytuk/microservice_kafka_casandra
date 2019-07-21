package it.discovery.order;

import it.discovery.order.domain.Product;
import it.discovery.order.repository.redis.ProductRepository;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableJpaRepositories("it.discovery.order.repository")
@EnableKafka
@ComponentScan("it.discovery")
@EnableRedisRepositories("it.discovery.order.repository.redis")
public class OrderApplication {
    @Autowired
    private ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @Bean
    public DefaultKafkaProducerFactory producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        return new DefaultKafkaProducerFactory(configProps,
                new StringSerializer(), new JsonSerializer());
    }

    @PostConstruct
    public void setup() {
        Product product = new Product();
        product.setId(1);
        product.setPrice(10);

        productRepository.save(product);

    }
}
