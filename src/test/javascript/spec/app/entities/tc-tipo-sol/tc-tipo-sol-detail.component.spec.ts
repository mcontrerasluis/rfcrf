import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RfcrfTestModule } from '../../../test.module';
import { TcTipoSolDetailComponent } from 'app/entities/tc-tipo-sol/tc-tipo-sol-detail.component';
import { TcTipoSol } from 'app/shared/model/tc-tipo-sol.model';

describe('Component Tests', () => {
  describe('TcTipoSol Management Detail Component', () => {
    let comp: TcTipoSolDetailComponent;
    let fixture: ComponentFixture<TcTipoSolDetailComponent>;
    const route = ({ data: of({ tcTipoSol: new TcTipoSol(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RfcrfTestModule],
        declarations: [TcTipoSolDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TcTipoSolDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TcTipoSolDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tcTipoSol on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tcTipoSol).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
