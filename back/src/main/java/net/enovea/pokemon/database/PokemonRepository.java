package net.enovea.pokemon.database;

import net.enovea.pokemon.api.dto.GenerationId;
import net.enovea.pokemon.api.dto.PokemonFormId;
import net.enovea.pokemon.api.dto.PokemonSpecies;
import net.enovea.pokemon.domain.Avatars;
import net.enovea.pokemon.domain.Generation;
import net.enovea.pokemon.domain.Pokemon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PokemonRepository {

    int index = -1;

    public Connection bddConnect() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/pokedb", "root", "");
    }

    public void deletePokemonTable(Connection connection) throws SQLException {
        try (var statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM pokemons");
        }
    }

    public void deleteGenerationTable(Connection connection) throws SQLException {
        try (var statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM generations");
        }
    }

    public void insertGenerations(Connection bddConnection, List<GenerationId> generationIds) throws SQLException {

        StringBuilder requestBuilder = new StringBuilder();

        requestBuilder.append("INSERT INTO generations (name, id) VALUES");
        var values = generationIds.stream().map(generationId -> "('" + generationId.getName() + "', '" + generationId.getId() + "')")
                .collect(Collectors.joining(",\n"));

        requestBuilder.append(values);
        try (var statement = bddConnection.createStatement()) {
            statement.executeUpdate(requestBuilder.toString());
        }
    }

    public Pokemon findPokemonGeneration(Pokemon pokemon, List<Generation> generationIdList) {
        for (Generation generationId : generationIdList) {
            for ( String pokemonSpecies : generationId.getPokemons()) {
                if (pokemonSpecies.equals(pokemon.getName())) {
                    pokemon.setGeneration_id(generationId.getId());
                    return pokemon;
                }
            }
        }
        return pokemon;
    }

    public void insertPokemonsDetails(Connection bddConnection, Stream<Pokemon> pokemons) throws SQLException {


        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append("INSERT INTO pokemons (front_default, back_default, id, type1, type2, generation_id, name, nickname) VALUES");
        var values = pokemons
                .filter(pokemon -> pokemon.getGeneration_id() != null)
                .map(pokemon -> "('" + pokemon.getAvatars().getFront_default() + "', '" +
                        pokemon.getAvatars().getBack_default() + "', '" +
                        pokemon.getId() + "', '" +
                        pokemon.getTypes()[0] + "', '" +
                        (pokemon.getTypes().length > 1 ? pokemon.getTypes()[1] : "") + "', '" +
                        pokemon.getGeneration_id() + "', '" +
                        pokemon.getName() + "', '" +
                        pokemon.getName()
                         + "')")
                .collect(Collectors.joining(",\n"));

        requestBuilder.append(values);
        try (var statement = bddConnection.createStatement()) {
            statement.executeUpdate(requestBuilder.toString());
        }

    }

}
