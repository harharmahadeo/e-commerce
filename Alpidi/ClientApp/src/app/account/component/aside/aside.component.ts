// Angular
import { Component, ElementRef, OnInit, Renderer2, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { combineLatest, Observable, Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MenuService } from '../../utils/menu/menu.service';
// Layout
//import { LayoutConfigService } from '../../../core/_base/layout';
// Auth
//import { AuthNoticeService } from '../../../core/auth';

@Component({
	selector: 'app-aside',
	templateUrl: './aside.component.html',
	styleUrls: ['./aside.component.scss'],
	encapsulation: ViewEncapsulation.None
})
export class AsideComponent implements OnInit {
	// Public properties
	today: number = Date.now();
  subscription: Subscription;
  activePanel$: Observable<string>;
  activeMenu: string;
  public index: number = 1;
	
	ngOnInit(): void {
		//this.translationService.setLanguage(this.translationService.getSelectedLanguage());
		//this.headerLogo = this.layoutConfigService.getLogo();

		//this.splashScreenService.hide();
	}
  constructor(
    public readonly menuService: MenuService,
    router: Router,
    activatedRoute: ActivatedRoute) {
    this.activePanel$ = activatedRoute.data.pipe(
      map((data) => data?.admin ? 'admin' : 'shopowner')
    );
    this.subscription = combineLatest([
      menuService.menu$,
      router.events
        .pipe(
          filter(e => e instanceof NavigationEnd),
          map((e: NavigationEnd) => e.url)
        )
    ])
      .subscribe(
        ([groups, link]) => {
          this.activeMenu = null;
          const root = groups[0].kins[0].link;
          for (const group of groups) {
            for (const menu of group.kins) {
              if (link == root + menu.link) {
                this.activeMenu = menu.class;
                return;
              }
            }
          }
        }
      );
    this.index = Math.floor(Math.random() * 2) + 1;
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
	
}
