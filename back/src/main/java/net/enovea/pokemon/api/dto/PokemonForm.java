package net.enovea.pokemon.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class PokemonForm {
   private List<PokemonUrl> results;
}
