package ma.enset.productsmvc;

import ma.enset.productsmvc.entities.Product;
import ma.enset.productsmvc.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication
public class ProductsMvcApplication {

    public static void main(String[] args) {

        SpringApplication.run(ProductsMvcApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return  args->{
            Product product1 = Product.builder()
                    .name("Computer")
                    .price(2342)
                    .quantity(21)
                    .build();
            productRepository.save(product1);
            Product product2 = Product.builder()
                    .name("iphone")
                    .price(2303)
                    .quantity(13)
                    .build();
            productRepository.save(product2);

            productRepository.findAll().forEach(p->System.out.println(p.toString()));
        };
    }
}
