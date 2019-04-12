import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IConsumo } from 'app/shared/model/consumo.model';

type EntityResponseType = HttpResponse<IConsumo>;
type EntityArrayResponseType = HttpResponse<IConsumo[]>;

@Injectable({ providedIn: 'root' })
export class ConsumoService {
    public resourceUrl = SERVER_API_URL + 'api/consumos';

    constructor(protected http: HttpClient) {}

    create(consumo: IConsumo): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(consumo);
        return this.http
            .post<IConsumo>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(consumo: IConsumo): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(consumo);
        return this.http
            .put<IConsumo>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IConsumo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IConsumo[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(consumo: IConsumo): IConsumo {
        const copy: IConsumo = Object.assign({}, consumo, {
            fecha: consumo.fecha != null && consumo.fecha.isValid() ? consumo.fecha.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((consumo: IConsumo) => {
                consumo.fecha = consumo.fecha != null ? moment(consumo.fecha) : null;
            });
        }
        return res;
    }
}
