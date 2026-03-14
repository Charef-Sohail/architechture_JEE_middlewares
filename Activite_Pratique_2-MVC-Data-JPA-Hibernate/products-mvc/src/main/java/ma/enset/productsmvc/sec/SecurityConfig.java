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
            User.withUsername("admin").password(encoder.encode("1234")).roles("USER", "ADMIN").build()
    );
}

@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    return http
            .formLogin(Customizer.withDefaults()) //fl->fl.loginPage("/login")
            .authorizeHttpRequests(ar->ar.requestMatchers("/user/**").hasRole("USER"))
            .authorizeHttpRequests(ar->ar.requestMatchers("/admin/**").hasRole("ADMIN"))
            .authorizeHttpRequests(ar->ar.requestMatchers("/public/**").permitAll())
            .authorizeHttpRequests(ar->ar.anyRequest().authenticated()) // il faut passer par l'auth
            .build();
}

}
