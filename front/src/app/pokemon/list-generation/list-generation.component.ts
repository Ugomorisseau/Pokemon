import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Generation} from "../generation";
import {CrudService} from "../crud.service";
import {Pokemon} from "../pokemon";

@Component({
  selector: 'app-list-generation',
  templateUrl: './list-generation.component.html',
  styleUrls: ['./list-generation.component.css']
})
export class ListGenerationComponent implements OnInit {

  id: number;
  generationList: Generation[];
  pokemonList: Pokemon[];

  constructor(private router: Router,
              private crudService: CrudService) {
  }



  setNumberPokemonsByGenerations(){
    this.generationList.forEach(generation => {
      switch (generation.name) {
        case "generation-i":
          return generation.nbPokemon = 151;
        case "generation-ii":
          return generation.nbPokemon = 100;
        case "generation-iii":
          return generation.nbPokemon = 135;
        case "generation-iv":
          return generation.nbPokemon = 107;
        case "generation-v":
          return generation.nbPokemon = 156;
        case "generation-vi":
          return generation.nbPokemon = 72;
        case "generation-vii":
          return generation.nbPokemon = 86;
        case "generation-viii":
          return generation.nbPokemon = 100;
      }
      return generation.nbPokemon = 0;
    })
  }

  goToTheCurrentGeneration(id: number) {
    this.router.navigate(['/generation/' + id + '/pokemons']);
  }

  ngOnInit() {
    this.crudService.getGenerations().subscribe((result) => {
      this.generationList = result;
      this.setNumberPokemonsByGenerations();
    });
    // this.crudService.getPokemonsByGenerationFromApi().subscribe((result) => {
    //   this.pokemonList = result;
    // });
  }
}

