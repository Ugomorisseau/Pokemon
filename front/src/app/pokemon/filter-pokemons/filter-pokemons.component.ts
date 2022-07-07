import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {PokemonService} from "../pokemon.service";
import {Pokemon} from "../pokemon";
import {CrudService} from "../crud.service";

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

  constructor(private crudService: CrudService) { }

  ngOnInit(): void {
    this.crudService.getPokemons().subscribe((result) => {
      this.pokemonList = result;
      this.pokemonTypes = this.crudService.getPokemonTypes();
    });
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
