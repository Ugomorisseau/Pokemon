import {Component, OnInit} from '@angular/core';
import {Pokemon} from "../pokemon";
import {ActivatedRoute, Router} from "@angular/router";
import {CrudService} from "../crud.service";

@Component({
  selector: 'app-list-pokemon',
  templateUrl: './list-pokemon.component.html',
  styleUrls: ['./list-pokemon.component.css']
})
export class ListPokemonComponent implements OnInit {

  generation_id: string | null;
  pokemonList: Pokemon[];
  filterList: Pokemon[] = [];

  constructor(private route: ActivatedRoute,
              private router: Router,
              private crudService: CrudService) {

  }

  ngOnInit() {
    this.generation_id = this.route.snapshot.paramMap.get('id');
    this.crudService.getPokemonsByGenerationFromApi(this.generation_id).subscribe((result) => {
      this.pokemonList = result;
      this.filterList = this.pokemonList;
    });
  }



goDetails(pokemon
:
Pokemon
)
{
  this.router.navigate(['/pokemon/' + pokemon.id]);
}

refreshList(filter
:
any
)
{
  this.filterList = this.pokemonList
    .filter(pokemon =>
      pokemon.nickname.toLowerCase().startsWith(filter.name.toLowerCase()))
    .filter(pokemon => (filter.types.some((type: any) => pokemon.types.includes(type))) || filter.types.length === 0);
}

changeFilter(filter
:
{
  name: string, types
:
  string[]
}
)
{
  this.refreshList(filter);
}

}


