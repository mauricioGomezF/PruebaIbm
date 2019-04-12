/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IbmTestModule } from '../../../test.module';
import { AsesorDetailComponent } from 'app/entities/asesor/asesor-detail.component';
import { Asesor } from 'app/shared/model/asesor.model';

describe('Component Tests', () => {
    describe('Asesor Management Detail Component', () => {
        let comp: AsesorDetailComponent;
        let fixture: ComponentFixture<AsesorDetailComponent>;
        const route = ({ data: of({ asesor: new Asesor(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IbmTestModule],
                declarations: [AsesorDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AsesorDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AsesorDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.asesor).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
