import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ListPokemonComponent} from "./list-pokemon/list-pokemon.component";
import {DetailPokemonComponent} from "./detail-pokemon/detail-pokemon.component";
import {BorderCardDirective} from "./border-card.directive";
import {ColorButtonDirectiveDirective} from "./color-button-directive.directive";
import {PokemonTypeColorPipe} from "./pokemon-type-color.pipe";
import {PokemonTypeColorBorderHoverPipe} from "./pokemon-type-color-border-hover.pipe";
import {RouterModule, Routes} from "@angular/router";
import {PokemonService} from "./pokemon.service";
import {FormsModule} from "@angular/forms";
import {PokemonEditFormComponent} from './pokemon-edit-form/pokemon-edit-form.component';
import {EditPokemonComponent} from './edit-pokemon/edit-pokemon.component';
import {ListGenerationComponent} from './list-generation/list-generation.component';
import {FilterPokemonsComponent} from './filter-pokemons/filter-pokemons.component';
import {NgImageSliderModule} from "ng-image-slider";
import {NavbarComponent} from "./navbar/navbar.component";
import {InfosComponent} from './infos/infos.component';
import { FormLoginComponent } from './form-login/form-login.component';
import { FormRegisterComponent } from './form-register/form-register.component';


const pokemonRoutes: Routes = [
  {path: 'login', component: FormLoginComponent},
  {path: 'register', component: FormRegisterComponent},
  {path: 'edit/pokemon/:id', component: EditPokemonComponent },
  {path: 'generation', component: ListGenerationComponent },
  {path: 'generation/1/pokemons', component: ListPokemonComponent},
  {path: 'pokemon/:id', component: DetailPokemonComponent},
  {path: 'infos', component: InfosComponent},
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
    NavbarComponent,
    InfosComponent,
    FormLoginComponent,
    FormRegisterComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild(pokemonRoutes),
    NgImageSliderModule
  ],
  providers: [PokemonService],
  exports: [
    NavbarComponent
  ]
})
export class PokemonModule { }
