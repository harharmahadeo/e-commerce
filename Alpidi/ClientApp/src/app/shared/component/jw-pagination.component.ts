import { Component, Input, Output, EventEmitter, OnInit, OnChanges, SimpleChanges } from '@angular/core';

@Component({
  selector: 'jw-pagination',
  template: `<ul *ngIf="pager.pages && pager.pages.length" class="pagination justify-content-center">  
  <li [ngClass]="{disabled:pager.currentPage === 1}" class="prev is-arrow">
      <span (click)="pagechange(pager.currentPage - 1)" class="page-link"><i class="fa fa-chevron-left"></i></span>
  </li>
  <li *ngFor="let page of pager.pages" [ngClass]="{active: pager.currentPage === page}">
      <span (click)="pagechange(page)">{{page}}</span>
  </li>
  <li [ngClass]="{disabled:pager.currentPage === pager.totalPages}" class="next is-arrow">
      <span (click)="pagechange(pager.currentPage + 1)" class="page-link"><i class="fa fa-chevron-right"></i></span>
  </li>
</ul>`,
  styleUrls: ['./jw-pagination.component.scss']
})

export class JwPaginationComponent implements OnInit, OnChanges {
  @Input() items: Array<any>;
  @Input() total: number;
  @Output() changePage = new EventEmitter<any>(true);
  @Input() initialPage = 1;
  @Input() pageSize = 10;
  @Input() maxPages = 10;
  pager: any = {};

  constructor(
  ) {
  }

  ngOnInit() { this.setPage(this.initialPage); }

  ngOnChanges(changes: SimpleChanges) {
    this.pager = this.paginate(this.total, this.initialPage, this.pageSize, this.maxPages);
  }

  private setPage(page: number) {
    // get new pager object for specified page
    this.pager = this.paginate(this.total, page, this.pageSize, this.maxPages);
  }

  private pagechange(page: number) {
    this.pager = this.paginate(this.total, page, this.pageSize, this.maxPages);
    this.changePage.emit(page);

  }

  private paginate(totalItems, currentPage, pageSize, maxPages) {
    if (currentPage === void 0) { currentPage = 1; }
    if (pageSize === void 0) { pageSize = 10; }
    if (maxPages === void 0) { maxPages = 10; }   
    // calculate total pages
    var totalPages = Math.ceil(totalItems / pageSize);
    // ensure current page isn't out of range
    if (currentPage < 1) {
      currentPage = 1;
    }
    else if (currentPage > totalPages) {
      currentPage = totalPages;
    }
    var startPage, endPage;
    if (totalPages <= maxPages) {
      // total pages less than max so show all pages
      startPage = 1;
      endPage = totalPages;
    }
    else {
      // total pages more than max so calculate start and end pages
      var maxPagesBeforeCurrentPage = Math.floor(maxPages / 2);
      var maxPagesAfterCurrentPage = Math.ceil(maxPages / 2) - 1;
      if (currentPage <= maxPagesBeforeCurrentPage) {
        // current page near the start
        startPage = 1;
        endPage = maxPages;
      }
      else if (currentPage + maxPagesAfterCurrentPage >= totalPages) {
        // current page near the end
        startPage = totalPages - maxPages + 1;
        endPage = totalPages;
      }
      else {
        // current page somewhere in the middle
        startPage = currentPage - maxPagesBeforeCurrentPage;
        endPage = currentPage + maxPagesAfterCurrentPage;
      }
    }
    // calculate start and end item indexes
    var startIndex = (currentPage - 1) * pageSize;
    var endIndex = Math.min(startIndex + pageSize - 1, totalItems - 1);
    // create an array of pages to ng-repeat in the pager control
    var pages = Array.from(Array((endPage + 1) - startPage).keys()).map(function (i) { return startPage + i; });
    // return object with all pager properties required by the view
    return {
      totalItems: totalItems,
      currentPage: currentPage,
      pageSize: pageSize,
      totalPages: totalPages,
      startPage: startPage,
      endPage: endPage,
      startIndex: startIndex,
      endIndex: endIndex,
      pages: pages
    };
  }

}
