package net.enovea.pokemon;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BackPokemonConfiguration {
    private final PokemonExternalAPI pokemonExternalAPI;
    private final PokemonRepository pokemonRepository;

    public BackPokemonConfiguration() {
        this.pokemonExternalAPI = new PokemonExternalAPI(objectMapper());
        this.pokemonRepository = new PokemonRepository();
    }

    @Bean
    public PokemonController pokemonController(PokemonExternalAPI pokemonExternalAPI, PokemonRepository pokemonRepository) {
        return new PokemonController(pokemonExternalAPI, pokemonRepository);
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
