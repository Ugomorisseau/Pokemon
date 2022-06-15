import {Directive, ElementRef, HostListener, Input} from '@angular/core';

@Directive({
  selector: '[appColorButtonDirective]'
})
export class ColorButtonDirectiveDirective {


  constructor(private element: ElementRef) {
  }

  @Input('appColorButtonDirective')
  set fontColor(color: string) {
    this.element.nativeElement.style.color = color;
  }
}
