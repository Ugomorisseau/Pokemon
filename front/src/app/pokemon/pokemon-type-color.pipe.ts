import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'pokemonTypeColor'
})
export class PokemonTypeColorPipe implements PipeTransform {
  transform(type: string): string {

    let color: string;

    switch (type) {
      case 'fire':
        color = 'red lighten-1';
        break;
      case 'water':
        color = 'blue lighten-1';
        break;
      case 'grass':
        color = 'green lighten-1';
        break;
      case 'bug':
        color = 'brown lighten-1';
        break;
      case 'normal':
        color = 'grey lighten-3';
        break;
      case 'flying':
        color = 'blue lighten-3';
        break;
      case 'poison':
        color = 'deep-purple accent-1';
        break;
      case 'fairy':
        color = 'pink lighten-4';
        break;
      case 'psychic':
        color = 'deep-purple darken-2';
        break;
      case 'electric':
        color = 'lime accent-1';
        break;
      case 'fighting':
        color = 'deep-orange';
        break;
      case 'ground':
        color = 'brown lighten-10';
        break;
      case 'rock':
        color = 'brown lighten-1';
        break;
      case 'steel':
        color = 'grey lighten-3';
        break;
      case 'ice':
        color = 'cyan lighten-1';
        break;
      case 'ghost':
        color = 'deep-purple darken-10';
        break;
      case 'dragon':
        color = 'deep-purple darken';
        break;
      default:
        color = 'grey';
        break;
    }

    return 'chip ' + color;

  }
}
