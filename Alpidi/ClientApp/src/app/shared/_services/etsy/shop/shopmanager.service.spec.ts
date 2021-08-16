import { TestBed } from '@angular/core/testing';
import { ShopManagerService } from './shopmanager.service';

describe('ShopManagerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ShopManagerService = TestBed.get(ShopManagerService);
    expect(service).toBeTruthy();
  });
});
