import {Component, OnInit} from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import {CurrencyService} from '../currencies/currencies.service';
import {ConverterService} from './converter.service';
import {Result} from './Result';

@Component({
    selector: 'converter',
    templateUrl: './converter.component.html',
    styleUrls: ['./converter.component.css']
})
export class ConverterComponent implements OnInit {

    currencies:string[] = null;
    result: Result;
    
    public conversionForm = this.fb.group({
        ccy1: ["USD", Validators.required],
        ccy2: ["AUD", Validators.required],
        amount: [100, Validators.required]
    });

    constructor(private currencyService: CurrencyService, 
        private converterService: ConverterService,
        private fb: FormBuilder) {
    
        this.result = new Result();
    }

    ngOnInit() {
        if (null == this.currencies ){
            this.currencyService.getCurrences().
                subscribe( curr => {
                    this.currencies = curr;
                }
            );
        }
    }
    
    public onSubmit(): void {
        this.converterService.convert(
            this.conversionForm.value.ccy1, 
            this.conversionForm.value.ccy2, 
            this.conversionForm.value.amount).
            subscribe( res => {
                this.result = res;
            }
        );
    }
}
