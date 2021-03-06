package net.enovea.pokemon.database;

import net.enovea.pokemon.domain.Generation;
import net.enovea.pokemon.domain.Pokemon;
import net.enovea.pokemon.domain.RepositoryPokemon;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PokemonRepository implements RepositoryPokemon {
    private Connection connection;

    public PokemonRepository(DataSource datasource) throws SQLException {
        this.connection = datasource.getConnection();
        //this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokedb", "root", "");
    }

    private void deletePokemonTable() throws SQLException {
        try (var statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM pokemons");
        }
    }

    private void deleteGenerationTable() throws SQLException {
        try (var statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM generations");
        }
    }

    public void insertGenerations(List<Generation> generationIds) throws SQLException {

        StringBuilder requestBuilder = new StringBuilder();

        requestBuilder.append("INSERT INTO generations (name, id, picture) VALUES");
        var values = generationIds.stream().map(generationId -> "('" + generationId.getName() + "', '"
                        + generationId.getId() + "' , '"
                        + generationId.getPicture() + "')")
                .collect(Collectors.joining(",\n"));

        requestBuilder.append(values);
        try (var statement = connection.createStatement()) {
            statement.executeUpdate(requestBuilder.toString());
        }
    }

    public void insertPokemonsDetails(List<Pokemon> pokemons) throws SQLException {


        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append("INSERT INTO pokemons (front_default, id, type1, type2, generation_id, name, nickname, attack, hp) VALUES");
        var values = pokemons.stream()
                .filter(pokemon -> pokemon.getGeneration_id() != null)
                .map(pokemon -> "('" + pokemon.getAvatars() + "', '" +
                        pokemon.getId() + "', '" +
                        pokemon.getTypes()[0] + "', '" +
                        (pokemon.getTypes().length > 1 ? pokemon.getTypes()[1] : "") + "', '" +
                        pokemon.getGeneration_id() + "', '" +
                        pokemon.getName() + "', '" +
                        pokemon.getName()
                        + "', '" +
                        pokemon.getAttack() + "', '" +
                        pokemon.getHp() + "')")
                .collect(Collectors.joining(",\n"));

        requestBuilder.append(values);
        try (var statement = connection.createStatement()) {
            statement.executeUpdate(requestBuilder.toString());
        }

    }

    @Override
    public void deleteTable() throws SQLException {
        deletePokemonTable();
        deleteGenerationTable();
    }

    private Pokemon getPokemon(int pokemon_id) throws SQLException {

        var pokemon = new Pokemon();
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append("SELECT * FROM pokemons WHERE id = ").append(pokemon_id);
        try (var statement = connection.createStatement()) {
            var result = statement.executeQuery(requestBuilder.toString());
            while (result.next()) {
                pokemon.setId(result.getInt("id"));
                pokemon.setName(result.getString("name"));
                pokemon.setAttack(result.getInt("attack"));
                pokemon.setHp(result.getInt("hp"));
                pokemon.setGeneration_id(result.getInt("generation_id"));
                pokemon.setAvatars(result.getString("front_default"));
                pokemon.setTypes(new String[]{result.getString("type1"), result.getString("type2")});
            }
        }
        return pokemon;
    }

    public List<Pokemon> getAllPokemonsOfThisGeneration(Integer generation_id) throws SQLException {
        var pokemons = new ArrayList<Pokemon>();
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append("SELECT id FROM pokemons WHERE generation_id = ").append(generation_id);
        try (var statement = connection.createStatement()) {
            var result = statement.executeQuery(requestBuilder.toString());
            while (result.next()) {
                pokemons.add(getPokemon(result.getInt("id")));
            }
        }
        return pokemons;
    }

    public Pokemon getPokemonById(int pokemon_id) throws SQLException {
        return getPokemon(pokemon_id);
    }

    public List<Pokemon> getPokemons() throws SQLException {
        var pokemons = new ArrayList<Pokemon>();
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append("SELECT id FROM pokemons");
        try (var statement = connection.createStatement()) {
            var result = statement.executeQuery(requestBuilder.toString());
            while (result.next()) {
                pokemons.add(getPokemon(result.getInt("id")));
            }
        }
        return pokemons;
    }

    private Generation getGeneration(int generation_id) throws SQLException {
        var generation = new Generation();
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append("SELECT * FROM generations WHERE id = ").append(generation_id);
        try (var statement = connection.createStatement()) {
            var result = statement.executeQuery(requestBuilder.toString());
            while (result.next()) {
                generation.setId(result.getInt("id"));
                generation.setName(result.getString("name"));
                generation.setPicture(result.getString("picture"));
            }
        }
        return generation;
    }

    public List<Generation> getGenerations() throws SQLException {
        var generations = new ArrayList<Generation>();
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append("SELECT id FROM generations");
        try (var statement = connection.createStatement()) {
            var result = statement.executeQuery(requestBuilder.toString());
            while (result.next()) {
                generations.add(getGeneration(result.getInt("id")));
            }
        }
        return generations;
    }
}
