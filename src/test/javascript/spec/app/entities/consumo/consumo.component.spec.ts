/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IbmTestModule } from '../../../test.module';
import { ConsumoComponent } from 'app/entities/consumo/consumo.component';
import { ConsumoService } from 'app/entities/consumo/consumo.service';
import { Consumo } from 'app/shared/model/consumo.model';

describe('Component Tests', () => {
    describe('Consumo Management Component', () => {
        let comp: ConsumoComponent;
        let fixture: ComponentFixture<ConsumoComponent>;
        let service: ConsumoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IbmTestModule],
                declarations: [ConsumoComponent],
                providers: []
            })
                .overrideTemplate(ConsumoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ConsumoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConsumoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Consumo(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.consumos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
