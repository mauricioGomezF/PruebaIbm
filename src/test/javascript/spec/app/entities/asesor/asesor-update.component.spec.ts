/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IbmTestModule } from '../../../test.module';
import { AsesorUpdateComponent } from 'app/entities/asesor/asesor-update.component';
import { AsesorService } from 'app/entities/asesor/asesor.service';
import { Asesor } from 'app/shared/model/asesor.model';

describe('Component Tests', () => {
    describe('Asesor Management Update Component', () => {
        let comp: AsesorUpdateComponent;
        let fixture: ComponentFixture<AsesorUpdateComponent>;
        let service: AsesorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IbmTestModule],
                declarations: [AsesorUpdateComponent]
            })
                .overrideTemplate(AsesorUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AsesorUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AsesorService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Asesor(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.asesor = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Asesor();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.asesor = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
