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
export class ListingService {

  redirect_uri = environment.etsy_redirect_uri;
  client_id = environment.etsy_client_id;
  secreate_key = environment.etsy_secreate_key;

  

  constructor(private apiservice: ApiService, public authService: AuthService) {
   
  }

  async listingdetails(listingid) {
    try {
      return await this.apiservice.get(`auth/etsy/getListingDetails?listingid=${listingid}`);
    }
    catch (e) {
      return of(undefined);
    }
  }

  async upload(files: FileList, listingid: number, shopid: number) {
    const formData: FormData = new FormData();
    for (let i = 0; i < files.length; i++) {
      formData.append('files', files[i])
    }
    //formData.append('files', file);
    formData.append('listingid', listingid.toString());
    formData.append('shopid', shopid.toString());
    return await this.apiservice.postJson(`auth/uploadVectorFiles`, formData);
  }
}
