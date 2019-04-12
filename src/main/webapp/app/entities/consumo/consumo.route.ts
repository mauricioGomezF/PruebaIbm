import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Consumo } from 'app/shared/model/consumo.model';
import { ConsumoService } from './consumo.service';
import { ConsumoComponent } from './consumo.component';
import { ConsumoDetailComponent } from './consumo-detail.component';
import { ConsumoUpdateComponent } from './consumo-update.component';
import { ConsumoDeletePopupComponent } from './consumo-delete-dialog.component';
import { IConsumo } from 'app/shared/model/consumo.model';

@Injectable({ providedIn: 'root' })
export class ConsumoResolve implements Resolve<IConsumo> {
    constructor(private service: ConsumoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IConsumo> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Consumo>) => response.ok),
                map((consumo: HttpResponse<Consumo>) => consumo.body)
            );
        }
        return of(new Consumo());
    }
}

export const consumoRoute: Routes = [
    {
        path: '',
        component: ConsumoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Consumos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ConsumoDetailComponent,
        resolve: {
            consumo: ConsumoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Consumos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ConsumoUpdateComponent,
        resolve: {
            consumo: ConsumoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Consumos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ConsumoUpdateComponent,
        resolve: {
            consumo: ConsumoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Consumos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const consumoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ConsumoDeletePopupComponent,
        resolve: {
            consumo: ConsumoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Consumos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
