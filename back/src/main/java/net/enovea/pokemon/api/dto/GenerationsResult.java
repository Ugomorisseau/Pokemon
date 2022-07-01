package net.enovea.pokemon.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class GenerationsResult {
    private List<PokemonUrl> results;
}
