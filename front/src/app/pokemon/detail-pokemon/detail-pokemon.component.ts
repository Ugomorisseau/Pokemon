import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Pokemon} from "../pokemon";
import {CrudService} from "../crud.service";

@Component({
  selector: 'app-detail-pokemon',
  templateUrl: './detail-pokemon.component.html',
  styleUrls: ['./detail-pokemon.component.css']
})
export class DetailPokemonComponent implements OnInit {

  pokemon: Pokemon;
  pokemonId: number | null;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private crudService: CrudService) {
  }

  ngOnInit() {
    this.pokemonId = this.route.snapshot.params['id'];
    this.crudService.getPokemonById(this.pokemonId).subscribe((result) => {
      this.pokemon = result;
      this.setNicknamePokemon();
    });
  }

  setNicknamePokemon() {
    switch (this.pokemon.nickname) {
      case "":
        return this.pokemon.nickname = this.pokemon.name;
    }
    return this.pokemon.nickname = this.pokemon.name;
  }

  goBack() {
    this.router.navigate(['/generation/1/pokemons']);
  }

  goToEditPokemon(pokemon: Pokemon) {
    this.router.navigate(['/edit/pokemon/', pokemon.id]);
  }

}
