import {Component, Input, OnInit} from '@angular/core';
import {PokemonService} from "../pokemon.service";
import {Pokemon} from "../pokemon";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-pokemon-edit-form',
  templateUrl: './pokemon-edit-form.component.html',
  styleUrls: ['./pokemon-edit-form.component.css']
})
export class PokemonEditFormComponent implements OnInit {

  @Input() pokemon: Pokemon;
  pokemonTypes: string[];

  constructor(private pokemonService: PokemonService,
              private router: Router) { }

  ngOnInit(): void {
    this.pokemonTypes = this.pokemonService.getPokemonTypeList();
  }

  hasAType(type: string): boolean{
    return this.pokemon.types.includes(type);
  }

  typeSelect($event: Event, type: string) {
    const checkbox = ($event.target as HTMLInputElement).checked;

    if (checkbox) {
      this.pokemon.types.push(type);
    } else {
      // méthode indexOf permet de comparer deux éléments => ===
      // méthode splice permer de modifier/supprimer un élément d'un tableau
      const index = this.pokemon.types.indexOf(type);
      this.pokemon.types.splice(index, 1);
    }
  }

  isTypesValid(type: string): boolean{
    if ( this.pokemon.types.length === 1 && this.hasAType(type)){
      return false;
    }

    if ( this.pokemon.types.length > 2 && !this.hasAType(type)){
      return false;
    }

    return true;
  }

  submitForm(){
    console.log("Submit Form" + this.pokemon);
    this.router.navigate(['/pokemon', this.pokemon.id]);
  }

}
