import { HttpClient } from '@angular/common/http';
import { Injectable, Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { User } from '../../../auth';
import { AuthService } from '../../../auth/_services/auth.service';
import { ApiService } from '../api.service';

const API_USERS_URL = `${environment.apiUrl}/auth`;

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private apiservice: ApiService, public authService: AuthService) {

  }
  // public methods
  async getusers() {
    try {
      return await this.apiservice.get(`auth/getalluser`);
    }
    catch (e) {
      
    }
  }
  async getunlinked() {
    try {
      return await this.apiservice.get(`auth/getalluser`);
    }
    catch (e) {

    }
  }
  async deleteuser(id: string) {
    try {
      return this.apiservice.postJson(`auth/deleteuser`, { id }).then((auth: User) => {
      })
    }
    catch (e) {

    }
  }
}
