import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITarjeta } from 'app/shared/model/tarjeta.model';
import { TarjetaService } from './tarjeta.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';

@Component({
    selector: 'jhi-tarjeta-update',
    templateUrl: './tarjeta-update.component.html'
})
export class TarjetaUpdateComponent implements OnInit {
    tarjeta: ITarjeta;
    isSaving: boolean;

    clientes: ICliente[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected tarjetaService: TarjetaService,
        protected clienteService: ClienteService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tarjeta }) => {
            this.tarjeta = tarjeta;
        });
        this.clienteService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICliente[]>) => response.body)
            )
            .subscribe((res: ICliente[]) => (this.clientes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tarjeta.id !== undefined) {
            this.subscribeToSaveResponse(this.tarjetaService.update(this.tarjeta));
        } else {
            this.subscribeToSaveResponse(this.tarjetaService.create(this.tarjeta));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarjeta>>) {
        result.subscribe((res: HttpResponse<ITarjeta>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackClienteById(index: number, item: ICliente) {
        return item.id;
    }
}
