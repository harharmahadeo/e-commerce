// Angular
import { Component, ElementRef, OnInit, Renderer2, ViewEncapsulation } from '@angular/core';
import { AuthService } from '../../../auth/_services/auth.service';
// Layout
//import { LayoutConfigService } from '../../../core/_base/layout';
// Auth
//import { AuthNoticeService } from '../../../core/auth';

@Component({
	selector: 'app-header',
	templateUrl: './header.component.html',
	styleUrls: ['./header.component.scss'],
	encapsulation: ViewEncapsulation.None
})
export class HeaderComponent implements OnInit {
	// Public properties
	today: number = Date.now();
	
	constructor(
		private el: ElementRef,
    private render: Renderer2,
    private authService: AuthService
		) {
	}
	ngOnInit(): void {
	}

  async signout() {
    await this.authService.logout();
  }
}
