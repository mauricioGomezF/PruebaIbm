import { NgModule } from '@angular/core';

import { IbmSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [IbmSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [IbmSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class IbmSharedCommonModule {}
