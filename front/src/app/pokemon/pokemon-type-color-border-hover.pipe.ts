import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'pokemonTypeColorBorderHover'
})
export class PokemonTypeColorBorderHoverPipe implements PipeTransform {

  transform(type: string): string {

    let colorBorder: string;

    switch (type) {
      case 'Feu':
        colorBorder = '#ef534b';
        break;
      case 'Eau':
        colorBorder = '#42A5F5';
        break;
      case 'Plante':
        colorBorder = '#66bb6a';
        break;
      case 'Insecte':
        colorBorder = '#8D6E63';
        break;
      case 'Normal':
        colorBorder = 'grey';
        break;
      case 'Vol':
        colorBorder = '#90CAF9';
        break;
      case 'Poison':
        colorBorder = '#B388FF';
        break;
      case 'Fée':
        colorBorder = 'pink lighten-4';
        break;
      case 'Psy':
        colorBorder = 'deep-purple darken-2';
        break;
      case 'Electrik':
        colorBorder = '#F4FF81';
        break;
      case 'Combat':
        colorBorder = 'deep-orange';
        break;
      default:
        colorBorder = 'grey';
        break;
    }

    return colorBorder;

  }

}
