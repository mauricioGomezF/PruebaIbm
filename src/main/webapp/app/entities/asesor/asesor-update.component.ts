import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IAsesor } from 'app/shared/model/asesor.model';
import { AsesorService } from './asesor.service';

@Component({
    selector: 'jhi-asesor-update',
    templateUrl: './asesor-update.component.html'
})
export class AsesorUpdateComponent implements OnInit {
    asesor: IAsesor;
    isSaving: boolean;

    constructor(protected asesorService: AsesorService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ asesor }) => {
            this.asesor = asesor;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.asesor.id !== undefined) {
            this.subscribeToSaveResponse(this.asesorService.update(this.asesor));
        } else {
            this.subscribeToSaveResponse(this.asesorService.create(this.asesor));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAsesor>>) {
        result.subscribe((res: HttpResponse<IAsesor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
