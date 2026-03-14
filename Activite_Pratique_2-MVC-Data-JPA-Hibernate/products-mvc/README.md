## Activité Pratique 2 — Application Web MVC avec Spring Data JPA & Spring Security

### 1. Entité JPA — `Product`

On crée l'entité `Product` annotée avec JPA et Bean Validation, en utilisant Lombok pour réduire le boilerplate.

```java
package ma.enset.productsmvc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @Builder
public class Product {
    @Id @GeneratedValue
    private Long id;
    @NotEmpty
    @Size(min=3, max=50)
    private String name;
    @Min(0)
    private double price;
    @Min(1)
    private int quantity;
}
```

### 2. Couche Repository — `ProductRepository`

On crée l'interface `ProductRepository` qui étend `JpaRepository`. Spring Data JPA génère automatiquement toutes les opérations CRUD.

```java
package ma.enset.productsmvc.repository;

import ma.enset.productsmvc.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
```

### 3. Couche Web — `ProductController`

Le contrôleur gère la navigation entre les pages, les opérations CRUD et la sécurité par routes (`/user/**` et `/admin/**`).

```java
package ma.enset.productsmvc.web;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import ma.enset.productsmvc.entities.Product;
import ma.enset.productsmvc.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {
    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/user/index")
    public String index(Model model){
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }

    @PostMapping("/admin/deleteProduct")
    public String delete(@RequestParam(name="id") Long id){
        productRepository.deleteById(id);
        return "redirect:/user/index";
    }

    @GetMapping("/admin/newProduct")
    public String add(Model model){
        model.addAttribute("product", new Product());
        return "new-product";
    }

    @PostMapping("/admin/saveProduct")
    public String saveProduct(@Valid Product product, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "new-product";
        productRepository.save(product);
        return "redirect:/user/index";
    }

    @GetMapping("/login")
    public String login(){ return "login"; }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:login";
    }

    @GetMapping("/notAuthorized")
    public String notAuthorized(){ return "errors/403"; }
}
```

### 4. Sécurité — `SecurityConfig`

On configure Spring Security avec des utilisateurs en mémoire et une `SecurityFilterChain` qui protège les routes par rôle.

```java
package ma.enset.productsmvc.sec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
        PasswordEncoder encoder = passwordEncoder();
        return new InMemoryUserDetailsManager(
            User.withUsername("user1").password(encoder.encode("1234")).roles("USER").build(),
            User.withUsername("user2").password(encoder.encode("1234")).roles("USER").build(),
            User.withUsername("admin").password(encoder.encode("1234")).roles("USER","ADMIN").build()
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .formLogin(fl -> fl.loginPage("/login").permitAll())
            .csrf(Customizer.withDefaults())
            .authorizeHttpRequests(ar -> ar.requestMatchers("/user/**").hasRole("USER"))
            .authorizeHttpRequests(ar -> ar.requestMatchers("/admin/**").hasRole("ADMIN"))
            .authorizeHttpRequests(ar -> ar.requestMatchers("/public/**","/css/**","/js/**","/images/**").permitAll())
            .authorizeHttpRequests(ar -> ar.anyRequest().authenticated())
            .exceptionHandling(eh -> eh.accessDeniedPage("/notAuthorized"))
            .build();
    }
}
```

### 5. Initialisation des données — `ProductsMvcApplication`

Au démarrage, un `CommandLineRunner` insère quelques produits de test en base.

```java
@SpringBootApplication
public class ProductsMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductsMvcApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return args -> {
            productRepository.save(Product.builder().name("Computer").price(2342).quantity(21).build());
            productRepository.save(Product.builder().name("iphone").price(2303).quantity(13).build());
            productRepository.findAll().forEach(p -> System.out.println(p.toString()));
        };
    }
}
```

### 6. Configuration — `application.properties`

```properties
spring.application.name=products-mvc

spring.datasource.url=jdbc:h2:mem:products-db
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create
spring.h2.console.enabled=true
server.port=8080
```
