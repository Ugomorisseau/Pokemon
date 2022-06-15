package net.enovea.pokemon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PokemonRepository {
    public Connection bddConnect() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/pokedb", "root", "");
    }

    public void deletePokemonTable(Connection connection) throws SQLException {
        try (var statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM pokemons");
        }
    }
    public void insertPokemonsDetails(Connection bddConnection, Stream<PokemonFormId> pokemons) throws SQLException {
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append("INSERT INTO pokemons (name, front_default, back_default, nickname, id, type1, type2) VALUES");
        var values =  pokemons.map(pokemon -> "('" + pokemon.getName() + "', '" +
                        pokemon.getSprites().getFront_default() + "', '" +
                        pokemon.getSprites().getBack_default() + "', '" +
                        pokemon.getName() + "', '" +
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
