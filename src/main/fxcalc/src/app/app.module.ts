import { BrowserModule } from '@angular/platform-browser';
import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { CurrencyService } from './currencies/currencies.service';
import { ConverterService } from './converter/converter.service';
import { RatesService } from './rates/rates.service';
import { ConverterComponent } from './converter/converter.component';
import { DirectratesComponent } from './directrates/directrates.component';
import { RatesmatrixComponent } from './ratesmatrix/ratesmatrix.component';

@NgModule({
  declarations: [
    AppComponent,
    ConverterComponent,
    DirectratesComponent,
    RatesmatrixComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpModule
  ],
  providers: [CurrencyService, ConverterService, RatesService],
  bootstrap: [AppComponent]
})
export class AppModule { }

platformBrowserDynamic().bootstrapModule(AppModule);
