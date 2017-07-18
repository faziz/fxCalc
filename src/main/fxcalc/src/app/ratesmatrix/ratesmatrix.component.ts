import {Component, OnInit} from '@angular/core';
import {RatesService} from '../rates/rates.service';
import {RateMatrix} from '../rates/RateMatrix';

@Component({
    selector: 'ratesmatrix',
    templateUrl: './ratesmatrix.component.html',
    styleUrls: ['./ratesmatrix.component.css']
})
export class RatesmatrixComponent implements OnInit {
    
    rateMatrix:RateMatrix[];

    constructor(private ratesService: RatesService) {}

    ngOnInit() {
        this.ratesService.getRatesMatrics().subscribe(
            res => {
                this.rateMatrix = res;
            }
        );
    }
}
