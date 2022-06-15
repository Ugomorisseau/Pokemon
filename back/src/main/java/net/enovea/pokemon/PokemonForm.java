package net.enovea.pokemon;

import lombok.Data;

import java.util.List;

@Data
public class PokemonForm {
   private int count;
   private String next;
   private String previous;
   private List<PokemonUrl> results;
}
