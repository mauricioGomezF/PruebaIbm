import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IConsumo } from 'app/shared/model/consumo.model';
import { AccountService } from 'app/core';
import { ConsumoService } from './consumo.service';

@Component({
    selector: 'jhi-consumo',
    templateUrl: './consumo.component.html'
})
export class ConsumoComponent implements OnInit, OnDestroy {
    consumos: IConsumo[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected consumoService: ConsumoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.consumoService
            .query()
            .pipe(
                filter((res: HttpResponse<IConsumo[]>) => res.ok),
                map((res: HttpResponse<IConsumo[]>) => res.body)
            )
            .subscribe(
                (res: IConsumo[]) => {
                    this.consumos = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInConsumos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IConsumo) {
        return item.id;
    }

    registerChangeInConsumos() {
        this.eventSubscriber = this.eventManager.subscribe('consumoListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
