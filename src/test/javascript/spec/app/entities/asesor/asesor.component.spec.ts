/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IbmTestModule } from '../../../test.module';
import { AsesorComponent } from 'app/entities/asesor/asesor.component';
import { AsesorService } from 'app/entities/asesor/asesor.service';
import { Asesor } from 'app/shared/model/asesor.model';

describe('Component Tests', () => {
    describe('Asesor Management Component', () => {
        let comp: AsesorComponent;
        let fixture: ComponentFixture<AsesorComponent>;
        let service: AsesorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [IbmTestModule],
                declarations: [AsesorComponent],
                providers: []
            })
                .overrideTemplate(AsesorComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AsesorComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AsesorService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Asesor(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.asesors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
