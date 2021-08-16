// Angular
import { Component, ElementRef, OnInit, Renderer2 } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { environment } from '../../../../environments/environment';
import { LinkedAccounts } from '../../../shared/_models/authorizedLinked.model';
import { EtsyAuthService } from '../../../shared/_services/etsy/auth/authorization.service';

@Component({
  selector: 'app-connect',
  templateUrl: './connect.component.html',
  styleUrls: ['./connect.component.scss']
})
export class ConnectComponent implements OnInit {

  redirect_uri = environment.etsy_redirect_uri;
  client_id = environment.etsy_client_id;
  secreate_key = environment.etsy_secreate_key;
  codeChallenge = '';
  codeVerifier = '';
  tokendetails: LinkedAccounts = undefined;
  linked_account_name: string = '';

  constructor(
    private toastr: ToastrService,
    private route: ActivatedRoute,
    private router: Router,
    private etsyAuthService: EtsyAuthService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(val => {
      this.route.queryParams.subscribe(params => {
        if (params['code'] !== undefined && params['code'] != '' && params['code'] != null) {
          this.etsyAuthService.getaccesstoken(params['code'], localStorage['varifier']).then((response: any) => {
            console.log(response);
            this.toastr.success('Your account was linked successfuly!');
            this.router.navigate(['account/shopowner/product']);
          });
        }
      });
    });
    
  }

  refreshaccesstoken() {
    if (this.tokendetails !== undefined) {
      this.etsyAuthService.refreshaccesstoken(this.tokendetails.kins.refreshtoken).then((response: any) => {
        console.log(response);
      });
    }
  }
  connect() {
    this.etsyAuthService.generatecodechallenge().then((response: any) => {
      if (response.code == 200) {
        if (response.codeChallenge !== '' && response.codeVerifier !== '') {
          this.codeChallenge = response.codeChallenge;
          this.codeVerifier = response.codeVerifier;
          localStorage['varifier'] = this.codeVerifier;

          var connecturl = 'https://www.etsy.com/oauth/connect?response_type=code&redirect_uri=' + environment.etsy_redirect_uri + '&scope=' + environment.etsy_scope + '&client_id=' + environment.etsy_client_id + '&state=' + environment.etsy_state + ' & code_challenge=' + this.codeChallenge + ' & code_challenge_method=S256';
          window.location.href = connecturl;
        }
      }
    });   
  }
}
