package net.enovea.pokemon;

import lombok.Data;

@Data
public class PokemonObject {
    private String name;
    private int id;
    private String nickname;
    private String back_default;
    private String front_default;
    private int generation_id;
    private String type1;
    private String type2;
}
