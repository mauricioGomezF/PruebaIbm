import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'cliente',
                loadChildren: './cliente/cliente.module#IbmClienteModule'
            },
            {
                path: 'tarjeta',
                loadChildren: './tarjeta/tarjeta.module#IbmTarjetaModule'
            },
            {
                path: 'consumo',
                loadChildren: './consumo/consumo.module#IbmConsumoModule'
            },
            {
                path: 'asesor',
                loadChildren: './asesor/asesor.module#IbmAsesorModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IbmEntityModule {}
