package net.enovea.pokemon.api.dto;

import lombok.Data;
import net.enovea.pokemon.domain.Avatars;

import java.util.List;

@Data
public class PokemonFormId {
    private int id;
    private String name;
    private PokemonSpecies pokemon;
    private Avatars sprites;
    private Types[] types;
    private Integer generation_id;
}
