import {Injectable} from "@angular/core";
import {Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Pokemon} from "./pokemon";
import {POKEMONS} from "./mock-pokemon-list";

@Injectable()
export class CrudService{
  protected componentUrl = "http://localhost:8080";
  pokemonById: Pokemon | undefined;

  constructor(
    private router: Router,
    private http: HttpClient
  ){}

  getPokemons(): Observable<any>{
    return this.http.get(this.componentUrl + "/pokemons");
  }

  getGenerations(): Observable<any>{
    return this.http.get(this.componentUrl + "/generations");
  }

  getPokemonsByGenerationFromApi(generation_id: string | null): Observable<any>{
    return this.http.get(this.componentUrl + "/generation/" +  generation_id + "/pokemons");
  }

  initDB() {
    var headers = new HttpHeaders();
    headers.set('Access-Control-Allow-Origin', '*');
    return this.http.post(this.componentUrl + "/init",{},{headers}).subscribe(() => console.log("bonjour 2"));
  }

  getPokemonById(id: number | null): Observable<any> {
    return this.http.get(this.componentUrl + "/pokemon/" + id);
  }

  getPokemonTypes(): string[] {
   return ['grass', 'fire', 'water', 'bug', 'normal', 'flying', 'poison', 'psychic', 'fairy', 'fighting', 'electric', 'ground', 'rock', 'steel', 'ice', 'ghost', 'dragon'];
  }



}
