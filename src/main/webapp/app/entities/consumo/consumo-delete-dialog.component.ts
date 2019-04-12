import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConsumo } from 'app/shared/model/consumo.model';
import { ConsumoService } from './consumo.service';

@Component({
    selector: 'jhi-consumo-delete-dialog',
    templateUrl: './consumo-delete-dialog.component.html'
})
export class ConsumoDeleteDialogComponent {
    consumo: IConsumo;

    constructor(protected consumoService: ConsumoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.consumoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'consumoListModification',
                content: 'Deleted an consumo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-consumo-delete-popup',
    template: ''
})
export class ConsumoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ consumo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ConsumoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.consumo = consumo;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/consumo', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/consumo', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
