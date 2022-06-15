import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ListPokemonComponent} from "./list-pokemon/list-pokemon.component";
import {DetailPokemonComponent} from "./detail-pokemon/detail-pokemon.component";
import {BorderCardDirective} from "./border-card.directive";
import {ColorButtonDirectiveDirective} from "./color-button-directive.directive";
import {PokemonTypeColorPipe} from "./pokemon-type-color.pipe";
import {PokemonTypeColorBorderHoverPipe} from "./pokemon-type-color-border-hover.pipe";
import {RouterModule, Routes} from "@angular/router";
import {PokemonService} from "./pokemon.service";
import {FormsModule} from "@angular/forms";
import { PokemonEditFormComponent } from './pokemon-edit-form/pokemon-edit-form.component';
import { EditPokemonComponent } from './edit-pokemon/edit-pokemon.component';
import { ListGenerationComponent } from './list-generation/list-generation.component';
import { FilterPokemonsComponent } from './filter-pokemons/filter-pokemons.component';


const pokemonRoutes: Routes = [
  {path: 'edit/pokemon/:id', component: EditPokemonComponent },
  {path: 'generation', component: ListGenerationComponent },
  {path: 'generation/1/pokemons', component: ListPokemonComponent},
  {path: 'pokemon/:id', component: DetailPokemonComponent},
];

@NgModule({
  declarations: [
    ListPokemonComponent,
    DetailPokemonComponent,
    BorderCardDirective,
    ColorButtonDirectiveDirective,
    PokemonTypeColorPipe,
    PokemonTypeColorBorderHoverPipe,
    PokemonEditFormComponent,
    EditPokemonComponent,
    ListGenerationComponent,
    FilterPokemonsComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild(pokemonRoutes)
  ],
  providers: [PokemonService],
})
export class PokemonModule { }
