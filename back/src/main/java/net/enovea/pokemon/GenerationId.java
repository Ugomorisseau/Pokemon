package net.enovea.pokemon;

import lombok.Data;

import java.util.List;

@Data
public class GenerationId {
    private int id;
    private String name;
    private PokemonSpecies[] pokemon_species;
}
