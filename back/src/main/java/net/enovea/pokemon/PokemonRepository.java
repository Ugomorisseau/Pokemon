package net.enovea.pokemon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
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

    public void insertGenerations(Connection bddConnection, List<GenerationId> generations) throws SQLException {

        StringBuilder requestBuilder = new StringBuilder();
        StringBuilder requestBuilder2 = new StringBuilder();

        requestBuilder.append("INSERT INTO generations (name, id) VALUES");
        var values = generations.stream().map(generation -> "('" + generation.getName() + "', '" + generation.getId() + "')")
                .collect(Collectors.joining(",\n"));

        requestBuilder.append(values);
        try (var statement = bddConnection.createStatement()) {
            statement.executeUpdate(requestBuilder.toString());
        }

        requestBuilder2.append("INSERT INTO pokemons (name, nickname) VALUES");
        var values2 = generations.stream().map(this::getPokemonsByGeneration)
                .collect(Collectors.joining(",\n"));
        requestBuilder2.append(values2);
        try (var statement = bddConnection.createStatement()) {
            statement.executeUpdate(requestBuilder2.toString());
        }
    }

    public String getPokemonsByGeneration(GenerationId generation){
        var result= Arrays.stream(generation.getPokemon_species())
                .map(pokemon -> "('" + pokemon.getName() + "' , '" + pokemon.getName() + "')")
                .collect(Collectors.joining(",\n"));
        System.out.println(result);
        return result;
    }

    public void insertPokemonsDetails(Connection bddConnection, Stream<PokemonFormId> pokemons) throws SQLException {
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append("INSERT INTO pokemons (front_default, back_default, id, type1, type2) VALUES");
        var values = pokemons.map(pokemon -> "('" + pokemon.getSprites().getFront_default() + "', '" +
                        pokemon.getSprites().getBack_default() + "', '" +
                        pokemon.getId() + "', '" +
                        pokemon.getTypes()[0].getType().getName() + "', '" +
                        (pokemon.getTypes().length > 1 ? pokemon.getTypes()[1].getType().getName() : "") + "')")
                .collect(Collectors.joining(",\n"));

        requestBuilder.append(values);
        try (var statement = bddConnection.createStatement()) {
            statement.executeUpdate(requestBuilder.toString());
        }

    }

}
