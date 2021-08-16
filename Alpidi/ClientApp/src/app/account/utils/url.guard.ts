import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { MenuService } from './menu/menu.service';
import { AuthService } from '../../auth';
import { EtsyAuthService } from '../../shared/_services/etsy/auth/authorization.service';

@Injectable()
export class UrlGuard implements CanActivate {

  constructor(private menuService: MenuService,
    private router: Router) {
   
  }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    this.menuService.initMenu();
    var role = this.menuService.role$.value;
    //return true;
    var match = state.url.match(`/account/${role}/?`);
    if (match) {
      return true;
    }
    else {      
      this.router.navigate([`/account/${role}/`]);
    }
  }
}
