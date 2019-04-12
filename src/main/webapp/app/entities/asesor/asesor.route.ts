import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Asesor } from 'app/shared/model/asesor.model';
import { AsesorService } from './asesor.service';
import { AsesorComponent } from './asesor.component';
import { AsesorDetailComponent } from './asesor-detail.component';
import { AsesorUpdateComponent } from './asesor-update.component';
import { AsesorDeletePopupComponent } from './asesor-delete-dialog.component';
import { IAsesor } from 'app/shared/model/asesor.model';

@Injectable({ providedIn: 'root' })
export class AsesorResolve implements Resolve<IAsesor> {
    constructor(private service: AsesorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAsesor> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Asesor>) => response.ok),
                map((asesor: HttpResponse<Asesor>) => asesor.body)
            );
        }
        return of(new Asesor());
    }
}

export const asesorRoute: Routes = [
    {
        path: '',
        component: AsesorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Asesors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AsesorDetailComponent,
        resolve: {
            asesor: AsesorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Asesors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AsesorUpdateComponent,
        resolve: {
            asesor: AsesorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Asesors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AsesorUpdateComponent,
        resolve: {
            asesor: AsesorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Asesors'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const asesorPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AsesorDeletePopupComponent,
        resolve: {
            asesor: AsesorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Asesors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
