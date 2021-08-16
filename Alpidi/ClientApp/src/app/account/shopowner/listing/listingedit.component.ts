// Angular
import { Component, ElementRef, OnInit, Renderer2, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ListingService } from 'src/app/shared/_services/etsy/listing/listing.service';
import { Listings } from '../../../shared/_models/listings.model';

@Component({
  selector: 'app-listingedit',
  templateUrl: './listingedit.component.html',
  styleUrls: ['./listingedit.component.scss']
})
export class ListingEditComponent implements OnInit {
  // Public properties

  selectedFiles?: FileList;

  listingid: string = '';
  listingdetails: Listings = undefined;
  title: string;
  img: any;

  constructor(
    private listingService: ListingService,
    private route: ActivatedRoute,
    private toastr: ToastrService
  ) {
  }
  ngOnInit(): void {
    this.route.params.subscribe(val => {
      this.route.params.subscribe(params => {
        this.listingid = params['listingid'] == undefined ? "" : params['listingid'];
        if (this.listingid !== '') {
          this.listingService.listingdetails(this.listingid).then((result) => {
            console.log(result);
            this.listingdetails = result;
            this.img = result.images;
            this.title = this.listingdetails.title;
          });
        }
      })
    })
  }


  /** Vector file uploaded functions   */

  selectFiles(event: any): void {
    this.selectedFiles = event.target.files;
  }

  uploadFiles(): void {
    if (this.selectedFiles) {
      this.listingService.upload(this.selectedFiles, this.listingdetails.listing_id, this.listingdetails.shop_id).then(
        (result: any) => {
          console.log(result);
          this.listingdetails.vectorfiles = result;
          this.toastr.success("File uploaded successfully!");
        },
        (err: any) => {
         
        });
    }
  }
}




