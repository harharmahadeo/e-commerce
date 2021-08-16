import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, Observable } from 'rxjs';
import { AuthService } from '../_services/auth.service';
import { Router } from '@angular/router';
import { User } from '../_models/user.model';
import { first } from 'rxjs/operators';
import { FacebookLoginProvider, GoogleLoginProvider, SocialAuthService } from 'angularx-social-login';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss'],
})
export class RegistrationComponent implements OnInit {
  hasError: boolean;
  isLoading$: Observable<boolean>;
  signup_user: User = new User();
  Roles: string[] = ['admin', 'shopowner', 'designer', 'printhouse'];
  selectedrole: string;

  constructor(
    private authService: AuthService,
    private socialAuthService: SocialAuthService,
    private toastr: ToastrService,
    private router: Router
  ) {
    this.signup_user.role = 'admin';
  }

  ngOnInit(): void {

  }

  async submit() {
    this.hasError = false;
    this.signup_user.role = this.selectedrole;
    this.authService.createUser(this.signup_user).then((response) => {
      if (response) {
        this.redirectToLogin();
      } else {
        this.toastr.error('User registration is failed!');
      }
    });
   
  }

  redirectToLogin() {
    this.router.navigate(['/auth/login']);
  }
}

