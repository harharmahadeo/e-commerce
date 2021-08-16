import { Injectable} from '@angular/core';
import { Observable, BehaviorSubject, of} from 'rxjs';
import { AuthModel, User } from '../_models/user.model';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment';
import { ApiService } from '../../shared/_services/api.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService  {

  private authLocalStorageToken = `${environment.appVersion}-${environment.authToken}`;

  currentUserSubject: BehaviorSubject<AuthModel> = new BehaviorSubject<AuthModel>(new AuthModel());
  currentUser$: Observable<AuthModel> = this.currentUserSubject.asObservable(); 
  isLogin: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);;

  get currentUserValue(): AuthModel {
    return this.currentUserSubject.value;
  }

  set currentUserValue(user: AuthModel) {
    this.currentUserSubject.next(user);
  }

  constructor(
    private apiService: ApiService,
    private router: Router
  ) {
    this.currentUserSubject = new BehaviorSubject<AuthModel>(this.apiService.getAuthFromLocalStorage());
    if (this.currentUserSubject.value.accessToken !== '') {
      this.isLogin.next(true);
    }
    this.currentUser$ = this.currentUserSubject.asObservable();
  }

  // public methods
  async login(email: string, password: string): Promise<AuthModel> {
    return this.apiService.post(`auth/signin`, { email, password }).then((auth: AuthModel) => {
      
      this.apiService.setAuthFromLocalStorage(auth);
      if (auth) {
        this.currentUserSubject = new BehaviorSubject<AuthModel>(auth);
        return auth;
      } else {        
        this.logout();
        return undefined;
      }     
    }).catch((err) => { return undefined });
  }

  logout() {
    localStorage.removeItem(this.authLocalStorageToken);
    localStorage.clear();
    this.currentUserSubject = new BehaviorSubject<AuthModel>(undefined);
    this.router.navigate(['/auth/login']);
  }

  createUser(user: User): Promise<AuthModel> {
    return this.apiService.post(`auth/signup`, user).then((auth: AuthModel) => {
      this.apiService.setAuthFromLocalStorage(auth);
      if (auth) {
        this.currentUserSubject = new BehaviorSubject<AuthModel>(auth);
        return auth;
      } else {
        return undefined;
      }
    }).catch((err) => { return undefined });
  }

}
