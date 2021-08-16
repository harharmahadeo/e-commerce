import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable, of, Subject, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { catchError, map } from 'rxjs/operators';
import { AuthModel } from '../../auth/_models/user.model';

const API_USERS_URL = `${environment.apiUrl}`;
const authLocalStorageToken = `${environment.appVersion}-${environment.authToken}`;

@Injectable()
export class ApiService {

  httpHeaders = new HttpHeaders({
    Authorization: `Bearer ${this.getAuthFromLocalStorage().accessToken}`,
  });

  constructor(
    private http: HttpClient,
    private route: ActivatedRoute) {
  }

  async postJson<T, T2>(api: string, info: T): Promise<T2> {
    return await this.http.post<T2>(`${API_USERS_URL}/${api}`, info, {
      headers: this.httpHeaders,
    }).toPromise();
  }
  async post<T, T2>(api: string, info: T): Promise<T2> {
    return await this.http.post<T2>(`${API_USERS_URL}/${api}`, info).toPromise();
  }

  async getRow<T>(url: string): Promise<T> {
    return await this.http.get<T>(`${url}`).toPromise();
  }

  async get(api: string): Promise<any> {
    console.log(this.httpHeaders);
    return this.http.get<any>(`${API_USERS_URL}/${api}`, {
      headers: this.httpHeaders,
    }).toPromise();
  }

  async LinkedAccountDetails() {
    try {
      return await this.postJson(`auth/etsy/getTokendetails`,'');
    }
    catch (e) {
      return of(undefined);
    }
  }

  // private methods
  setAuthFromLocalStorage(auth: AuthModel): boolean {
    // store auth authToken/refreshToken/epiresIn in local storage to keep user logged in between page refreshes
    if (auth && auth.accessToken) {
      localStorage.setItem(authLocalStorageToken, JSON.stringify(auth));
      return true;
    }
    return false;
  }

  getAuthFromLocalStorage(): AuthModel {
    try {      
      return JSON.parse(localStorage.getItem(authLocalStorageToken) || '{}');
    } catch (error) {
      console.error(error);
      return JSON.parse('{}');
    }
  }

  handleError(error: HttpErrorResponse) {
    let errorMessage = 'Unknown error!';
    if (error.error instanceof ErrorEvent) {
      // Client-side errors
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side errors
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(error);
  }
}
