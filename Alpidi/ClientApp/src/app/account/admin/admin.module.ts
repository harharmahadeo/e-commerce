// Angular
import { ModuleWithProviders, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent } from './dashboard/dashboard.component';
import { ProductComponent } from './product/product.component';
import { UserComponent } from './user/user.component';
import { AddUserComponent } from './addedituser/adduser.component';
import { ProductdetailComponent } from './productdetail/productdetail.component';
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild([
      { path: '', redirectTo: 'user' },
      { path: 'user', component: UserComponent },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'product', component: ProductComponent },
      { path: 'addedituser', component: AddUserComponent },
      { path: 'productdetail', component: ProductdetailComponent }
    ]),
  ],
  providers: [],
  exports: [],
  declarations: [
    DashboardComponent,
    ProductComponent,
    UserComponent,
    AddUserComponent,
    ProductdetailComponent
  ]
})

export class AdminModule {

}
