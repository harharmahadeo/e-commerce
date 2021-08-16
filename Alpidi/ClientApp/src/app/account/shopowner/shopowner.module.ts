// Angular
import {  NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { ConnectComponent } from './connect/connect.component';
import { ListingComponent } from './listing/listing.component';
import { ListingEditComponent } from './listing/listingedit.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild([
      { path: '', redirectTo: 'connect' },
      { path: 'connect', component: ConnectComponent },
      { path: 'listing', component: ListingComponent },
      { path: 'listing/edit/:listingid', component: ListingEditComponent }
    ]),
  ],
  providers: [],
  exports: [],
  declarations: [
    ConnectComponent,
    ListingComponent,
    ListingEditComponent
  ]
})

export class ShopOwnerModule {

}
