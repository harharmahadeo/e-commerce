import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Observable, Subscription } from 'rxjs';
//import { AuthService } from '../_services/auth.service';
import { first } from 'rxjs/operators';

enum ErrorStates {
  NotSubmitted,
  HasError,
  NoError,
}

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss'],
})
export class ForgotPasswordComponent implements OnInit {
  //forgotPasswordForm: FormGroup;
  //errorState: ErrorStates = ErrorStates.NotSubmitted;
  //errorStates = ErrorStates;
  //isLoading$: Observable<boolean>;

  // private fields
  //private unsubscribe: Subscription[] = []; // Read more: => https://brianflove.com/2016/12/11/anguar-2-unsubscribe-observables/
  constructor(
    private fb: FormBuilder
   // private authService: AuthService
  ) {
   // this.isLoading$ = this.authService.isLoading$;
  }

  ngOnInit(): void {
    
  }

 

  submit() {
    
  }
}
