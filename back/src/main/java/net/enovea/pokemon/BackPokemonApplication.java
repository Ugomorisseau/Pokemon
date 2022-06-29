package net.enovea.pokemon;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Stream;

//@SpringBootApplication BIm ca compile car c'est dans commentaire
public class BackPokemonApplication {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static void main(String[] args) {
        PokemonExternalAPI pokemonExternalAPI = new PokemonExternalAPI(OBJECT_MAPPER);
        PokemonRepository pokemonRepository = new PokemonRepository();
        try {
            Connection bddConnection = pokemonRepository.bddConnect();
            pokemonRepository.deletePokemonTable(bddConnection);
            pokemonRepository.deleteGenerationTable(bddConnection);

            List<PokemonUrl> generationInfos = pokemonExternalAPI.getGenerationsUrl();
            List<GenerationId> generationDetails = pokemonExternalAPI.retrieveGenerationsDetails(generationInfos);
            pokemonRepository.insertGenerations(bddConnection, generationDetails);

            List<PokemonUrl> pokemonInfos = pokemonExternalAPI.getPokemonsUrl();
            Stream<PokemonFormId> pokemonsDetails = pokemonExternalAPI.retrievePokemonsDetails(pokemonInfos);
            pokemonRepository.insertPokemonsDetails(bddConnection, pokemonsDetails);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
