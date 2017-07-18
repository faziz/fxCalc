import {Injectable} from '@angular/core';
import {Http, Response, Headers} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {RateMatrix} from './RateMatrix';

@Injectable()
export class RatesService {
    
    private directRates:string = "/api/calc/drates";
    private ratesMatrics:string = "/api/calc/rmatrix";

    constructor(private http: Http) {}

    public getDirectRates(): Observable<Object[][]> {
        return this.http.get(this.directRates, {headers: this.getHeaders()}).
            map(res => res.json());
    }
    
    public getRatesMatrics(): Observable<RateMatrix[]> {
        return this.http.get(this.ratesMatrics, {headers: this.getHeaders()}).
            map(res => res.json());
    }

    private getHeaders() {
        let headers = new Headers();
        headers.append('Accept', 'application/json');
        return headers;
    }
}
