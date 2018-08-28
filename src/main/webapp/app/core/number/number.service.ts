import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INumber } from './number.model';

@Injectable({ providedIn: 'root' })
export class NumberService {
    private resourceUrl = SERVER_API_URL + 'api/number';

    constructor(private http: HttpClient) {}

    publish(number: INumber): Observable<HttpResponse<INumber>> {
        const url = `${this.resourceUrl}?number=${number.number}&operator=${number.operator}`;
        return this.http.post<INumber>(url, number, { observe: 'response' });
    }

    porting(number: INumber, operator: string): Observable<HttpResponse<INumber>> {
        const url = `${this.resourceUrl}/porting?number=${number.number}&operatorFrom=${number.operator}&operatorTo=${operator}`;
        return this.http.post<INumber>(url, number, { observe: 'response' });
    }

    query(req?: any): Observable<HttpResponse<INumber[]>> {
        return this.http.get<INumber[]>(this.resourceUrl + '/' + req, { observe: 'response' });
    }
}
