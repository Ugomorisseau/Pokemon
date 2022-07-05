package net.enovea.pokemon.domain;

import lombok.Data;
import net.enovea.pokemon.api.dto.Type;

import java.util.List;

@Data
public class Pokemon {
    private int id;
    private String name;
    private String avatars;
    private String[] types;
    private Integer generation_id;
    private int attack;
    private int hp;
}
