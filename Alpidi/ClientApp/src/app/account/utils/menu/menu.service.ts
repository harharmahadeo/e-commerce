import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ApiService } from '../../../shared/_services/api.service';
import { admin as Admin } from './admin';
import { MenuGroup } from './menu.model';
import { shopowner as ShopOwner } from './shopowner';

@Injectable({
  providedIn: 'root'
})
export class MenuService {
  menu$: BehaviorSubject<MenuGroup[]>;
  role$: BehaviorSubject<string>;

  constructor(private apiService: ApiService) {

  }
  initMenu() {
    const profile = this.apiService.getAuthFromLocalStorage();
    if (profile.accessToken !== undefined) {
      this.role$ = new BehaviorSubject<string>(profile.role);

      if (profile.role == 'admin') {
        this.menu$ = new BehaviorSubject<MenuGroup[]>(Admin);
      }
      else if (profile.role == 'shopowner') {
        this.menu$ = new BehaviorSubject<MenuGroup[]>(ShopOwner);
      }
    }
  }
}
