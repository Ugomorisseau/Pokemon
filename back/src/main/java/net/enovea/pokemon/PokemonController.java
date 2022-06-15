package net.enovea.pokemon;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PokemonController {
    private PokemonFormId pokemonFormId;

    @GetMapping("/Pokedex")
    void printPokemon(){
        System.out.println(pokemonFormId.getName());
    }

}
