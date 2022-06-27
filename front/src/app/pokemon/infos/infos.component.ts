import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-infos',
  templateUrl: './infos.component.html',
  styleUrls: ['./infos.component.css']
})
export class InfosComponent implements OnInit {

  constructor(private router : Router) { }

  public clicked : boolean = false;

  goToHome(){
    this.clicked = true;
    setTimeout(() => {
      this.router.navigate(['/home']);
    },1000);
  }

  ngOnInit(): void {
  }

}
