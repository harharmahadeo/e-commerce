import { TestBed } from '@angular/core/testing';
import { EtsyAuthService } from './authorization.service';

describe('EtsyAuthService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EtsyAuthService = TestBed.get(EtsyAuthService);
    expect(service).toBeTruthy();
  });
});
