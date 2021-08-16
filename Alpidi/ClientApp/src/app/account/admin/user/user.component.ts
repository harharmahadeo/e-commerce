// Angular
import { Component, ElementRef, OnInit, Renderer2, ViewEncapsulation } from '@angular/core';
import { of } from 'rxjs';
import { catchError, finalize, map } from 'rxjs/operators';
import { User } from '../../../auth';
import { UserService } from '../../../shared/_services/user/user.service';

@Component({
	selector: 'app-user',
	templateUrl: './user.component.html',
	styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
	// Public properties
  today: number = Date.now();
  users: User[] = undefined;
	constructor(
		private el: ElementRef,
    private render: Renderer2,
    private userService: UserService
		) {
  }
  ngOnInit(): void{
    this.userService.getusers().then((result: any) => {
      this.users = result;
    });
  }
  unlinked(): void {
    this.userService.getusers().then((result: any) => {
      this.users = result;
    });
  }
  deleteuser(id: string): void {
    console.log(id);
    this.userService.deleteuser(id).then((result: any) => {
      this.users = result;
    });
  }
}
