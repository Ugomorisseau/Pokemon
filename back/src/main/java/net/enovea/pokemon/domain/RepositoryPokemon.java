package net.enovea.pokemon.domain;

import java.sql.Connection;
import java.sql.SQLException;

public interface RepositoryPokemon {
    void deleteTable() throws SQLException;
}
