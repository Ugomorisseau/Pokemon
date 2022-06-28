import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  public clicked : boolean = false;

  constructor(private router: Router) {
  }

  goToPokedex() {
    this.clicked = true;
    setTimeout( () => {
      this.router.navigate(['/generation']);
    })
  }


  goToHome(){
    this.clicked = true;
    setTimeout(() => {
      this.router.navigate(['/home']);
    },2000);
  }


  goToInfos(){
    this.clicked = true;
    setTimeout(() => {
      this.router.navigate(['/infos']);
    },2000);
  }

  goToLogin(){
    this.clicked = true;
    setTimeout(() => {
      this.router.navigate(['/login']);
    },2000);
  }

  goToRegister(){
    this.clicked = true;
    setTimeout(() => {
      this.router.navigate(['/register']);
    },2000);
  }

  ngOnInit(): void {
  }

}
