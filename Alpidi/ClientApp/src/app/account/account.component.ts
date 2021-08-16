// Angular
import { Component, ElementRef, OnInit, Renderer2, ViewEncapsulation } from '@angular/core';
// Layout
//import { LayoutConfigService } from '../../../core/_base/layout';
// Auth
//import { AuthNoticeService } from '../../../core/auth';

@Component({
	selector: 'app-account',
	templateUrl: './account.component.html',
	styleUrls: ['./account.component.scss'],
	encapsulation: ViewEncapsulation.None
})
export class AccountComponent implements OnInit {
	// Public properties
	today: number = Date.now();
	
	constructor(
		private el: ElementRef,
		private render: Renderer2,
		) {
	}
	ngOnInit(): void {
		
	}	
}
