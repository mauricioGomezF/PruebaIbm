import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IbmSharedModule } from 'app/shared';
import {
    ConsumoComponent,
    ConsumoDetailComponent,
    ConsumoUpdateComponent,
    ConsumoDeletePopupComponent,
    ConsumoDeleteDialogComponent,
    consumoRoute,
    consumoPopupRoute
} from './';

const ENTITY_STATES = [...consumoRoute, ...consumoPopupRoute];

@NgModule({
    imports: [IbmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ConsumoComponent,
        ConsumoDetailComponent,
        ConsumoUpdateComponent,
        ConsumoDeleteDialogComponent,
        ConsumoDeletePopupComponent
    ],
    entryComponents: [ConsumoComponent, ConsumoUpdateComponent, ConsumoDeleteDialogComponent, ConsumoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IbmConsumoModule {}
