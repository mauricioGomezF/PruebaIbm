/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { IbmTestModule } from '../../../test.module';
import { ConsumoUpdateComponent } from 'app/entities/consumo/consumo-update.component';
import { ConsumoService } from 'app/entities/consumo/consumo.service';
import { Consumo } from 'app/shared/model/consumo.model';

describe('Component Tests', () => {
    describe('Consumo Management Update Component', () => {
        let comp: ConsumoUpdateComponent;
        let fixture: ComponentFixture<ConsumoUpdateComponent>;
        let service: ConsumoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IbmTestModule],
                declarations: [ConsumoUpdateComponent]
            })
                .overrideTemplate(ConsumoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ConsumoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConsumoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Consumo(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.consumo = entity;
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
                    const entity = new Consumo();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.consumo = entity;
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
