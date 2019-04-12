import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAsesor } from 'app/shared/model/asesor.model';

type EntityResponseType = HttpResponse<IAsesor>;
type EntityArrayResponseType = HttpResponse<IAsesor[]>;

@Injectable({ providedIn: 'root' })
export class AsesorService {
    public resourceUrl = SERVER_API_URL + 'api/asesors';

    constructor(protected http: HttpClient) {}

    create(asesor: IAsesor): Observable<EntityResponseType> {
        return this.http.post<IAsesor>(this.resourceUrl, asesor, { observe: 'response' });
    }

    update(asesor: IAsesor): Observable<EntityResponseType> {
        return this.http.put<IAsesor>(this.resourceUrl, asesor, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAsesor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAsesor[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
