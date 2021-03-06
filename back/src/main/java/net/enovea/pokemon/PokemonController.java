package net.enovea.pokemon;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.enovea.pokemon.api.PokemonExternalAPI;
import net.enovea.pokemon.database.PokemonRepository;
import net.enovea.pokemon.domain.Generation;
import net.enovea.pokemon.domain.Pokemon;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PokemonController {
    private final PokemonExternalAPI pokemonExternalAPI;
    private final PokemonRepository pokemonRepository;


    public PokemonController(PokemonExternalAPI pokemonExternalAPI, PokemonRepository pokemonRepository) {
        this.pokemonExternalAPI = pokemonExternalAPI;
        this.pokemonRepository = pokemonRepository;
    }

    @PostMapping("/init")
    void Init() throws SQLException, IOException {
        pokemonRepository.deleteTable();
        List<Generation> generations = pokemonExternalAPI.getGenerations();
        pokemonRepository.insertGenerations(generations);
        List<Pokemon> pokemons = pokemonExternalAPI.getPokemons(generations);
        pokemonRepository.insertPokemonsDetails(pokemons);
    }

    @GetMapping("/generations")
    List<Generation> Generations() throws SQLException {
        return pokemonRepository.getGenerations();
    }

    @GetMapping("/pokemons")
    List<Pokemon> Pokemons() throws SQLException {
        return pokemonRepository.getPokemons();
    }

    @GetMapping("/generation/{id}/pokemons")
    List<Pokemon> PokemonsByGeneration(@PathVariable Integer id) throws SQLException {
        return pokemonRepository.getAllPokemonsOfThisGeneration(id);
    }

    @GetMapping("/pokemon/{id}")
    Pokemon PokemonById(@PathVariable Integer id) throws SQLException {
        return pokemonRepository.getPokemonById(id);
    }
}
