package net.enovea.pokemon;

import lombok.Data;

@Data
public class PokemonFormId {
    private int id;
    private String name;
    private Pokemon pokemon;
    private Sprites sprites;
    private Types[] types;
}
