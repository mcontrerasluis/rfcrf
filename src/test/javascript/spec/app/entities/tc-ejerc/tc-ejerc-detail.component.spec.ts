import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RfcrfTestModule } from '../../../test.module';
import { TcEjercDetailComponent } from 'app/entities/tc-ejerc/tc-ejerc-detail.component';
import { TcEjerc } from 'app/shared/model/tc-ejerc.model';

describe('Component Tests', () => {
  describe('TcEjerc Management Detail Component', () => {
    let comp: TcEjercDetailComponent;
    let fixture: ComponentFixture<TcEjercDetailComponent>;
    const route = ({ data: of({ tcEjerc: new TcEjerc(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RfcrfTestModule],
        declarations: [TcEjercDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TcEjercDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TcEjercDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tcEjerc on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tcEjerc).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
