// Angular
import { Component, OnDestroy, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SocialAuthService, GoogleLoginProvider, SocialUser, FacebookLoginProvider } from 'angularx-social-login';
import { Observable } from 'rxjs';
import { AuthModel, User } from '../_models/user.model';
import { AuthService } from '../_services/auth.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  encapsulation: ViewEncapsulation.None
})
export class LoginComponent implements OnInit, OnDestroy {
  username: string = "";
  password: string = "";
  remember = true;
  showPassword = false;

  returnUrl: string = '';
  isLoading$: Observable<boolean>;
  hasError: boolean = false;;
  signup_user: User = new User();
  routerpath: string = '';
  constructor(
    private route: ActivatedRoute,
    private authService: AuthService,
    private socialAuthService: SocialAuthService,
    private toastr: ToastrService,
    private router: Router) { }

  ngOnInit(): void {
    if (this.route.snapshot.queryParams['returnUrl'.toString()] !== undefined) {
      this.returnUrl = this.route.snapshot.queryParams['returnUrl'.toString()];
    }
    else {
      if (this.authService.currentUserValue) {
        if (this.authService.currentUserValue.accessToken != '' && this.authService.currentUserValue.accessToken !== undefined) {
          this.returnUrl = this.authService.currentUserValue.role == undefined ? 'admin' : this.authService.currentUserValue.role;
          this.router.navigate([`/account/${this.returnUrl}`]);
        }
      }
    }   
  }
  loginWithGoogle(): void {
    this.socialAuthService.signIn(GoogleLoginProvider.PROVIDER_ID)
      .then(async (user) => {
        this.signup_user.email = user.email;
        this.signup_user.firstname = user.firstName;
        this.signup_user.lastname = user.lastName;
        this.signup_user.password = '';
        this.signup_user.provider = user.provider;
        this.signup_user.photo = user.photoUrl;
        this.signup_user.role = 'shopowner';
        this.hasError = false;
        this.authService.createUser(this.signup_user).then((response) => {
          if (response) {
            this.router.navigate(['/account/shopowner']);
          } else {
            this.hasError = true;
          }
        });

      });
  }
  ngOnDestroy(): void {

  }
  async submit() {
    this.hasError = false;
    this.authService.login(this.username, this.password).then((user) => {
      if (user) {
        if (this.returnUrl == '') {
          this.returnUrl = '/account/' + user.role;
        }
        this.router.navigate([this.returnUrl]);
      } else {
        this.toastr.error('Login attempt failed!');
      }
    });

  }
}
