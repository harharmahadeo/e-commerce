// Angular
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

// Translate
import { AccountComponent } from './account.component';
import { AsideComponent } from './component/aside/aside.component';
import { HeaderComponent } from './component/header/header.component';
import { FooterComponent } from './component/footer/footer.component';
import { UrlGuard } from './utils/url.guard';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild([
      {
        path: '',
        component: AccountComponent,
        children: [
          { path: 'admin', canActivate: [UrlGuard], loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule) },
          { path: 'shopowner', canActivate: [UrlGuard], loadChildren: () => import('./shopowner/shopowner.module').then(m => m.ShopOwnerModule) }
        ]
      }
    ])
  ],
  providers: [UrlGuard],
  exports: [AccountComponent],
  declarations: [
    AccountComponent,
    HeaderComponent,
    AsideComponent,
    FooterComponent
  ]
})

export class AccountModule { }
