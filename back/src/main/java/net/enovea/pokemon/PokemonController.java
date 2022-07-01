package net.enovea.pokemon;

import net.enovea.pokemon.api.PokemonExternalAPI;
import net.enovea.pokemon.database.PokemonRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@RestController
public class PokemonController {
    private final PokemonExternalAPI pokemonExternalAPI;
    private final PokemonRepository pokemonRepository;

    public PokemonController(PokemonExternalAPI pokemonExternalAPI, PokemonRepository pokemonRepository) {
        this.pokemonExternalAPI = pokemonExternalAPI;
        this.pokemonRepository = pokemonRepository;
    }

    @GetMapping("/home")
    void home() throws SQLException {
        Connection connection = pokemonRepository.bddConnect();
        pokemonRepository.deletePokemonTable(connection);
    }

    @GetMapping("/Pokedex/Generations")
    void goToGenerations() throws IOException, SQLException {
        var generations = pokemonExternalAPI.retrieveGenerationsDetails(pokemonExternalAPI.getGenerationsUrl());
        Connection connection = pokemonRepository.bddConnect();
        pokemonRepository.insertGenerations(connection, generations);
    }








}
