import {Injectable} from '@angular/core';
import {Http, Headers} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';

@Injectable()
export class CurrencyService {
    
    private url:string = "/api/calc/currencies";
    
    constructor(private http: Http) {}

    getCurrences(): Observable<string[]> {
        return this.http.get(this.url, {headers: this.getHeaders()}).
            map(res => res.json());
    }
    
    private getHeaders() {
        let headers = new Headers();
        headers.append('Accept', 'application/json');
        return headers;
    }
}
