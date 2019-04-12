import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAsesor } from 'app/shared/model/asesor.model';
import { AsesorService } from './asesor.service';

@Component({
    selector: 'jhi-asesor-delete-dialog',
    templateUrl: './asesor-delete-dialog.component.html'
})
export class AsesorDeleteDialogComponent {
    asesor: IAsesor;

    constructor(protected asesorService: AsesorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.asesorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'asesorListModification',
                content: 'Deleted an asesor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-asesor-delete-popup',
    template: ''
})
export class AsesorDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ asesor }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AsesorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.asesor = asesor;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/asesor', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/asesor', { outlets: { popup: null } }]);
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
