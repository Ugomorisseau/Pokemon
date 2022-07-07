package net.enovea.pokemon.domain;

import lombok.Data;

import java.util.List;

@Data
public class Generation {
    private String name;
    private List<String> pokemons;
    private Integer id;
    private String picture;
}

