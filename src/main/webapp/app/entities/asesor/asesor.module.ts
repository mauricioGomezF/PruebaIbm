import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IbmSharedModule } from 'app/shared';
import {
    AsesorComponent,
    AsesorDetailComponent,
    AsesorUpdateComponent,
    AsesorDeletePopupComponent,
    AsesorDeleteDialogComponent,
    asesorRoute,
    asesorPopupRoute
} from './';

const ENTITY_STATES = [...asesorRoute, ...asesorPopupRoute];

@NgModule({
    imports: [IbmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [AsesorComponent, AsesorDetailComponent, AsesorUpdateComponent, AsesorDeleteDialogComponent, AsesorDeletePopupComponent],
    entryComponents: [AsesorComponent, AsesorUpdateComponent, AsesorDeleteDialogComponent, AsesorDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IbmAsesorModule {}
