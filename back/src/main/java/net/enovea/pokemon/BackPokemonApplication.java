package net.enovea.pokemon;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.enovea.pokemon.database.PokemonRepository;
import net.enovea.pokemon.api.dto.GenerationId;
import net.enovea.pokemon.api.dto.PokemonFormId;
import net.enovea.pokemon.api.PokemonExternalAPI;
import net.enovea.pokemon.domain.Generation;
import net.enovea.pokemon.domain.Pokemon;

import java.sql.Connection;
import java.util.List;

//@SpringBootApplication BIm ca compile car c'est dans commentaire
public class BackPokemonApplication {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static void main(String[] args) {
        PokemonExternalAPI pokemonExternalAPI = new PokemonExternalAPI(OBJECT_MAPPER);
        PokemonRepository pokemonRepository = new PokemonRepository();
        try {

            // bdd connect
            Connection bddConnection = pokemonRepository.bddConnect();
            // vide les tables pokemon et generation
            pokemonRepository.deletePokemonTable(bddConnection);
            pokemonRepository.deleteGenerationTable(bddConnection);


            // Get generations
            List<Generation> generationIds = pokemonExternalAPI.getGenerations();
            // Insert generations
            pokemonRepository.insertGenerations(bddConnection, generationIds);


            // Get pokemons
            List<Pokemon> pokemons = pokemonExternalAPI.getPokemons(generationIds);
            // Insert pokemons
            pokemonRepository.insertPokemonsDetails(bddConnection, pokemons.stream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
