import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {PokemonModule} from "./pokemon/pokemon.module";
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import {FormsModule} from "@angular/forms";
import { HomeComponent } from './home/home.component';
import { NgImageSliderModule } from 'ng-image-slider';
import {HttpClientModule} from "@angular/common/http";
import {PokemonService} from "./pokemon/pokemon.service";

@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent,
    HomeComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    PokemonModule,
    AppRoutingModule,
    NgImageSliderModule,
    HttpClientModule,
  ],
  providers: [PokemonService],
  exports: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
