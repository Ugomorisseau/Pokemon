import {Component, OnInit} from '@angular/core';
import {Generation} from "../pokemon/generation";
import {CrudService} from "../pokemon/crud.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  generationList: Generation[];

  constructor(private crudService: CrudService) { }

  setInfosOfGenerations() {
    for (let generation of this.generationList) {
      generation.thumbImage = generation.picture ;
      generation.image = generation.picture ;
      generation.title = generation.name ;
    }
    }

  ngOnInit(): void {
    this.crudService.getGenerations().subscribe(
      (result) => {
        this.generationList = result;
        this.setInfosOfGenerations();
      }
    );
  }
}

