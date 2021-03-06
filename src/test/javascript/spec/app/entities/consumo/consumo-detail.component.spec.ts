/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IbmTestModule } from '../../../test.module';
import { ConsumoDetailComponent } from 'app/entities/consumo/consumo-detail.component';
import { Consumo } from 'app/shared/model/consumo.model';

describe('Component Tests', () => {
    describe('Consumo Management Detail Component', () => {
        let comp: ConsumoDetailComponent;
        let fixture: ComponentFixture<ConsumoDetailComponent>;
        const route = ({ data: of({ consumo: new Consumo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IbmTestModule],
                declarations: [ConsumoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ConsumoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ConsumoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.consumo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
