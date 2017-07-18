import {Component, OnInit} from '@angular/core';
import {RatesService} from '../rates/rates.service';

@Component({
    selector: 'directrates',
    templateUrl: './directrates.component.html',
    styleUrls: ['./directrates.component.css']
})
export class DirectratesComponent implements OnInit {
    
    directRates:Object[][];
    

    constructor(private ratesService: RatesService) {
    }

    ngOnInit() {
        this.ratesService.getDirectRates().subscribe(
            res => {
                this.directRates = res;
            }
        );
    }
}
