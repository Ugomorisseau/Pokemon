import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'pokemonTypeColorBorderHover'
})
export class PokemonTypeColorBorderHoverPipe implements PipeTransform {

  transform(type: string): string {

    let colorBorder: string;

    switch (type) {
      case 'fire':
        colorBorder = '#ef534b';
        break;
      case 'water':
        colorBorder = '#42A5F5';
        break;
      case 'grass':
        colorBorder = '#66bb6a';
        break;
      case 'bug':
        colorBorder = '#8D6E63';
        break;
      case 'normal':
        colorBorder = 'grey';
        break;
      case 'flying':
        colorBorder = '#90CAF9';
        break;
      case 'poison':
        colorBorder = '#B388FF';
        break;
      case 'fairy':
        colorBorder = 'pink lighten-4';
        break;
      case 'psychic':
        colorBorder = 'deep-purple darken-2';
        break;
      case 'electric':
        colorBorder = '#F4FF81';
        break;
      case 'fighting':
        colorBorder = 'deep-orange';
        break;
      default:
        colorBorder = 'grey';
        break;
    }

    return colorBorder;

  }

}
