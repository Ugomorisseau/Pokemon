package net.enovea.pokemon;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.enovea.pokemon.api.PokemonExternalAPI;
import net.enovea.pokemon.database.PokemonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class BackPokemonConfiguration {

    @Bean
    public PokemonController pokemonController(PokemonExternalAPI pokemonExternalAPI, PokemonRepository pokemonRepository) {
        return new PokemonController(pokemonExternalAPI, pokemonRepository);
    }

    @Bean
    public PokemonRepository pokemonRepository(DataSource dataSource) throws SQLException {
        return new PokemonRepository(dataSource);
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Bean
    PokemonExternalAPI pokemonExternalAPI(ObjectMapper objectMapper) {
        return new PokemonExternalAPI(objectMapper);
    }
}
