import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAsesor } from 'app/shared/model/asesor.model';
import { AccountService } from 'app/core';
import { AsesorService } from './asesor.service';

@Component({
    selector: 'jhi-asesor',
    templateUrl: './asesor.component.html'
})
export class AsesorComponent implements OnInit, OnDestroy {
    asesors: IAsesor[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected asesorService: AsesorService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.asesorService
            .query()
            .pipe(
                filter((res: HttpResponse<IAsesor[]>) => res.ok),
                map((res: HttpResponse<IAsesor[]>) => res.body)
            )
            .subscribe(
                (res: IAsesor[]) => {
                    this.asesors = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAsesors();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAsesor) {
        return item.id;
    }

    registerChangeInAsesors() {
        this.eventSubscriber = this.eventManager.subscribe('asesorListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
