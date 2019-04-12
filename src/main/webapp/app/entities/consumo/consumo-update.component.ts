import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IConsumo } from 'app/shared/model/consumo.model';
import { ConsumoService } from './consumo.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';

@Component({
    selector: 'jhi-consumo-update',
    templateUrl: './consumo-update.component.html'
})
export class ConsumoUpdateComponent implements OnInit {
    consumo: IConsumo;
    isSaving: boolean;

    clientes: ICliente[];
    fecha: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected consumoService: ConsumoService,
        protected clienteService: ClienteService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ consumo }) => {
            this.consumo = consumo;
            this.fecha = this.consumo.fecha != null ? this.consumo.fecha.format(DATE_TIME_FORMAT) : null;
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
        this.consumo.fecha = this.fecha != null ? moment(this.fecha, DATE_TIME_FORMAT) : null;
        if (this.consumo.id !== undefined) {
            this.subscribeToSaveResponse(this.consumoService.update(this.consumo));
        } else {
            this.subscribeToSaveResponse(this.consumoService.create(this.consumo));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IConsumo>>) {
        result.subscribe((res: HttpResponse<IConsumo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
