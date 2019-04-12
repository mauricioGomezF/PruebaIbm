import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAsesor } from 'app/shared/model/asesor.model';

@Component({
    selector: 'jhi-asesor-detail',
    templateUrl: './asesor-detail.component.html'
})
export class AsesorDetailComponent implements OnInit {
    asesor: IAsesor;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ asesor }) => {
            this.asesor = asesor;
        });
    }

    previousState() {
        window.history.back();
    }
}
