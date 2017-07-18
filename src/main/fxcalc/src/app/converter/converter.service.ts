import {Injectable} from '@angular/core';
import {Http, Response, Headers} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import {Result} from './Result';

@Injectable()
export class ConverterService {

    constructor(private http: Http) {}
    
    convert(ccy1: string, ccy2: string, amount: number): Observable<Result> {
        var url:string = `/api/calc?ccy1=${ccy1}&ccy2=${ccy2}&amount=${amount}`;
        return this.http.get(url, {headers: this.getHeaders()}).
            map(res => res.json());
    }
    
    private getHeaders() {
        let headers = new Headers();
        headers.append('Accept', 'application/json');
        return headers;
    }

}
