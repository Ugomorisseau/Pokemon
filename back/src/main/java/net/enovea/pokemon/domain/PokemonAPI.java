package net.enovea.pokemon.domain;

import net.enovea.pokemon.api.dto.GenerationId;
import net.enovea.pokemon.api.dto.PokemonFormId;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface PokemonAPI {
    List<Pokemon> getPokemons(List<Generation> generationIds) throws IOException;
    List<Generation> getGenerations() throws SQLException, IOException;
}
