import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IbmSharedModule } from 'app/shared';
import {
    ClienteComponent,
    ClienteDetailComponent,
    ClienteUpdateComponent,
    ClienteDeletePopupComponent,
    ClienteDeleteDialogComponent,
    clienteRoute,
    clientePopupRoute
} from './';

const ENTITY_STATES = [...clienteRoute, ...clientePopupRoute];

@NgModule({
    imports: [IbmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ClienteComponent,
        ClienteDetailComponent,
        ClienteUpdateComponent,
        ClienteDeleteDialogComponent,
        ClienteDeletePopupComponent
    ],
    entryComponents: [ClienteComponent, ClienteUpdateComponent, ClienteDeleteDialogComponent, ClienteDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IbmClienteModule {}
