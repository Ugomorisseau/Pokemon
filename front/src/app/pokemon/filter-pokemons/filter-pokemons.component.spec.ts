import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilterPokemonsComponent } from './filter-pokemons.component';

describe('FilterPokemonsComponent', () => {
  let component: FilterPokemonsComponent;
  let fixture: ComponentFixture<FilterPokemonsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FilterPokemonsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FilterPokemonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
