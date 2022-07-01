package net.enovea.pokemon.api.dto;

import lombok.Data;
import net.enovea.pokemon.api.dto.PokemonFormId;
import net.enovea.pokemon.domain.Pokemon;

import java.util.List;

@Data
public class GenerationId {
    private int id;
    private String name;
    private List<PokemonSpecies> pokemon_species;
}
