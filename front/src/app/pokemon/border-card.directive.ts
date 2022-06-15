import {Directive, ElementRef, HostListener, Input} from '@angular/core';

@Directive({
  selector: '[pokemonBorderCard]'
})
export class BorderCardDirective {

  private initialWidthBorder : number = 1.5;
  private hoverWidthBorder : number = 3;
  private initialColor : string = 'grey';
  private defaultColor : string = 'black';
  private defaultHeight : number = 220;
  private defaultFontSize : number = 22;

  constructor(private element: ElementRef) {
  this.setHeight(this.defaultHeight);
  this.setBorderColor(this.initialWidthBorder, this.initialColor);
  this.setFontSize(this.defaultFontSize);
  }

  @Input('pokemonBorderCard') borderColor : string;

  @HostListener('mouseenter') onMouseEnter() {
    this.setBorderColor(this.hoverWidthBorder, this.borderColor);
  }

  @HostListener('mouseleave') onMouseLeave() {
    this.setBorderColor(this.initialWidthBorder, this.initialColor);
  }

  // exemple d'utilisation : setHeight(200) => permet de définir la hauteur d'un élément;
  setHeight(height: number) {
    this.element.nativeElement.style.height = height + 'px';
  }

  setBorderColor(width: number, color: string) {
    this.element.nativeElement.style.borderColor = color;
    this.element.nativeElement.style.borderStyle = 'solid';
    this.element.nativeElement.style.borderWidth = width + 'px';
    this.element.nativeElement.style.borderRadius = '10px';
  }

  setFontSize(size: number){
    this.element.nativeElement.style.fontSize = size + 'px';
  }
}
