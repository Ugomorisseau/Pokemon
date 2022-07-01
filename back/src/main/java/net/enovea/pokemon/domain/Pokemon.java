package net.enovea.pokemon.domain;

import lombok.Data;

import java.util.List;

@Data
public class Pokemon {
    private int id;
    private String name;
    private Avatars avatars;
    private String[] types;
    private Integer generation_id;
}
