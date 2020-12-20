import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RfcrfTestModule } from '../../../test.module';
import { TcTipoImpDetailComponent } from 'app/entities/tc-tipo-imp/tc-tipo-imp-detail.component';
import { TcTipoImp } from 'app/shared/model/tc-tipo-imp.model';

describe('Component Tests', () => {
  describe('TcTipoImp Management Detail Component', () => {
    let comp: TcTipoImpDetailComponent;
    let fixture: ComponentFixture<TcTipoImpDetailComponent>;
    const route = ({ data: of({ tcTipoImp: new TcTipoImp(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RfcrfTestModule],
        declarations: [TcTipoImpDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TcTipoImpDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TcTipoImpDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tcTipoImp on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tcTipoImp).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
