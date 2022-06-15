import {Component, OnInit} from '@angular/core';
import {Pokemon} from "../pokemon";
import {ActivatedRoute, Router} from "@angular/router";
import {PokemonService} from "../pokemon.service";
import {filter} from "rxjs";

@Component({
  selector: 'app-list-pokemon',
  templateUrl: './list-pokemon.component.html',
  styleUrls: ['./list-pokemon.component.css']
})
export class ListPokemonComponent implements OnInit {

  pokemonList: Pokemon[];
  filterList: Pokemon[] = [];

  constructor(private route: ActivatedRoute,
              private router: Router,
              private pokemonService: PokemonService) {
  }

  ngOnInit() {
    this.pokemonList = this.pokemonService.getPokemonList();
    this.filterList = this.pokemonService.getPokemonList();
  }

  goDetails(pokemon: Pokemon) {
    this.router.navigate(['/pokemon/' + pokemon.id]);
  }

  refreshList(filter: any) {
    this.filterList = this.pokemonList
      .filter(pokemon =>
        pokemon.name.toLowerCase().startsWith(filter.name.toLowerCase()))
      .filter(pokemon => (filter.types.some((type: any) => pokemon.types.includes(type))) || filter.types.length === 0);
  }

  changeFilter(filter: { name: string, types: string[] }) {
    this.refreshList(filter);
  }

}


