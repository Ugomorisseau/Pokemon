package net.enovea.pokemon;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@EnableAutoConfiguration
@Import(BackPokemonConfiguration.class)
public class BackPokemonApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackPokemonApplication.class, args);
    }
}
