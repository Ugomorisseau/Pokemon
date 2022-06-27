import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Generation} from "../generation";
import {GENERATIONS} from "../mock-generations-list";

@Component({
  selector: 'app-list-generation',
  templateUrl: './list-generation.component.html',
  styleUrls: ['./list-generation.component.css']
})
export class ListGenerationComponent implements OnInit {

  generationList: Generation[] = GENERATIONS;

  constructor(private router: Router) {
  }

  goToFirstGeneration(){
    this.router.navigate(['/generation/1/pokemons']);
  }

  ngOnInit() {
  }
}

