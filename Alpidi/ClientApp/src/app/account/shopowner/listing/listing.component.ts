// Angular
import { Component, ElementRef, OnInit, Renderer2 } from '@angular/core';
import { Listings } from '../../../shared/_models/listings.model';
import { lstshop, shop, shop_section, shop_section_response } from '../../../shared/_models/shop.model';
import { ShopManagerService } from '../../../shared/_services/etsy/shop/shopmanager.service';

@Component({
  selector: 'app-product',
  templateUrl: './listing.component.html',
  styleUrls: ['./listing.component.scss']
})
export class ListingComponent implements OnInit {

  shop_list: shop = new shop();
  shop_section: shop_section_response = undefined;
  shop_listing: Listings = undefined;
  constructor(
    private shopMangerService: ShopManagerService
  ) { }

  ngOnInit(): void {
    this.shopMangerService.getshopdetails().then((response) => {
      if (response !== undefined) {
        this.shop_list.shop_id = response.shop_id;
        this.shop_list.shop_name = response.shop_name;
        this.getCategories();
      }
    });
    this.shopMangerService.getshoplsiting().then((response) => {
      if (response !== undefined) {
        console.log(response);
        this.shop_listing = response;
        console.log("checking response id");
        
      }
    });
  }
  getCategories() {
    this.shopMangerService.getshopcategoy(this.shop_list.shop_id).then((response) => {
      this.shop_section = response;
      this.shop_section.results = this.shop_section.results.filter((x) => (x.active_listing_count != 0));
    });
  }
}
