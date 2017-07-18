/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { RatesService } from './rates.service';

describe('RatesService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RatesService]
    });
  });

  it('should ...', inject([RatesService], (service: RatesService) => {
    expect(service).toBeTruthy();
  }));
});
