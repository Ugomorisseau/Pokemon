import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Generation} from "../pokemon/generation";
import {GENERATIONS} from "../pokemon/mock-generations-list";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  generationList: Generation[] = GENERATIONS;

  constructor() { }


  ngOnInit(): void {
  }

}

