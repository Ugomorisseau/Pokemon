import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private router : Router) { }

  public clicked : boolean = false;

  goToGeneration(){
    this.clicked = true;
    setTimeout(() => {
      this.router.navigate(['/generation']);
    },2000);
  }



  ngOnInit(): void {
  }

}

