import { Component, OnInit } from '@angular/core';
import { AuthService } from './auth/_services/auth.service';
@Component({
  selector: 'app-logout',
  template: '<div>></div>',
})
export class LogoutComponent implements OnInit {

  constructor(private readonly loginService: AuthService) { }

  ngOnInit() {
    this.loginService.logout();
  }
}
