/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IbmTestModule } from '../../../test.module';
import { AsesorDeleteDialogComponent } from 'app/entities/asesor/asesor-delete-dialog.component';
import { AsesorService } from 'app/entities/asesor/asesor.service';

describe('Component Tests', () => {
    describe('Asesor Management Delete Component', () => {
        let comp: AsesorDeleteDialogComponent;
        let fixture: ComponentFixture<AsesorDeleteDialogComponent>;
        let service: AsesorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IbmTestModule],
                declarations: [AsesorDeleteDialogComponent]
            })
                .overrideTemplate(AsesorDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AsesorDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AsesorService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
