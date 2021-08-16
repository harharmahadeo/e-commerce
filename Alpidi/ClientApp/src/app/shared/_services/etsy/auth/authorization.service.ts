import { Injectable } from '@angular/core';
import { environment } from '../../../../../environments/environment';
import { ApiService } from '../../api.service';
import { AuthService } from '../../../../auth';
import { LinkedAccounts, TokenDetails } from '../../../_models/authorizedLinked.model';
import { BehaviorSubject, of } from 'rxjs';
const API_USERS_URL = `${environment.apiUrl}/auth/etsy`;

@Injectable({
  providedIn: 'root'
})
export class EtsyAuthService {

  redirect_uri = environment.etsy_redirect_uri;
  client_id = environment.etsy_client_id;
  secreate_key = environment.etsy_secreate_key;

  currentLinkedAccountsSubject: BehaviorSubject<LinkedAccounts> = new BehaviorSubject<LinkedAccounts>(new LinkedAccounts());

  constructor(private apiservice: ApiService, public authService: AuthService) {

    if (localStorage['lnkAccount'] === undefined) {
      this.apiservice.LinkedAccountDetails().then((response: any) => {
        if (response !== undefined && response !== null) {
          const lnkAccounts = new LinkedAccounts();
          lnkAccounts.name = 'etsy';
          lnkAccounts.kins = new TokenDetails();
          lnkAccounts.kins.setToken(response);
          this.currentLinkedAccountsSubject.next(lnkAccounts);
          localStorage['lnkAccount'] = JSON.stringify(lnkAccounts);
        }
        else {
          this.currentLinkedAccountsSubject.next(undefined);
        }
      });
    }
    else {
      this.currentLinkedAccountsSubject.next(JSON.parse(localStorage['lnkAccount']));
    }
  }  

  async generatecodechallenge() {
    try {
      return await this.apiservice.get(`auth/etsy/generatecodechallenge`);
    }
    catch (e) {

    }
  }
  async getaccesstoken(code, code_varifier) {
    try {
      return await this.apiservice.get(`auth/etsy/getaccesstoken?code=${code}&code_verifier=${code_varifier}&authtoken=${this.authService.currentUserValue.accessToken}`);
    }
    catch (e) {

    }
  }
  async refreshaccesstoken(refreshtoken) {
    try {
      return await this.apiservice.get(`auth/etsy/getrefreshtoken?refresh_token=${refreshtoken}&authtoken=${this.authService.currentUserValue.accessToken}`);
    }
    catch (e) {

    }
  }
  async getuser() {
    try {
      return await this.apiservice.get(`auth/etsy/getuser?authtoken=${this.authService.currentUserValue.accessToken}`);
    }
    catch (e) {
      return of(undefined)
    }
  }
}
