// Angular
import { Component, ElementRef, OnInit, Renderer2, ViewEncapsulation } from '@angular/core';
// Layout
//import { LayoutConfigService } from '../../../core/_base/layout';
// Auth
//import { AuthNoticeService } from '../../../core/auth';

@Component({
	selector: 'app-adduser',
	templateUrl: './adduser.component.html',
	styleUrls: ['./adduser.component.scss'],
	encapsulation: ViewEncapsulation.None
})
export class AddUserComponent implements OnInit {
	// Public properties
	today: number = Date.now();
	
	constructor(
		private el: ElementRef,
		private render: Renderer2,
		) {
	}
	ngOnInit(): void {
		//this.translationService.setLanguage(this.translationService.getSelectedLanguage());
		//this.headerLogo = this.layoutConfigService.getLogo();

		//this.splashScreenService.hide();
	}

	
}
