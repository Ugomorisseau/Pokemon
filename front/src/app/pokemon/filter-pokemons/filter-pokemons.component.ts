import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {PokemonService} from "../pokemon.service";
import {Pokemon} from "../pokemon";

@Component({
  selector: 'app-filter-pokemons',
  templateUrl: './filter-pokemons.component.html',
  styleUrls: ['./filter-pokemons.component.css']
})
export class FilterPokemonsComponent implements OnInit {

  searchOpen: boolean = false;
  filterOpen: boolean = false;
  types: Map<string, boolean> = new Map<string, boolean>([]);
  typesArray: string[] = [];
  pokemonList: Pokemon[];
  pokemonTypes: string[];
  name: string = '';

  @Output() filterChange = new EventEmitter<{name: string, types: string[]}>();

  constructor(private pokemonService: PokemonService) { }

  ngOnInit(): void {
    this.pokemonList = this.pokemonService.getPokemonList();
    this.pokemonTypes = this.pokemonService.getPokemonTypeList();
  }

  nameFilter(){
    // this.name += namePart.key;
    console.log(this.name);
    this.emitFilter();
  }

  toggleTypeFilter(type:string){
    this.types.set(type, !(this.types.get(type)??false));
    this.typesArray = Array.from(this.types.keys()).filter(key => this.types.get(key));
    this.emitFilter();
  }

  emitFilter(){
    this.filterChange.emit({name: this.name, types: this.typesArray});
  }

  filerMenuOpen() {
    this.filterOpen = !this.filterOpen;
  }

  searchMenuOpen() {
    this.searchOpen = !this.searchOpen;
  }

}
