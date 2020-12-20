import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RfcrfTestModule } from '../../../test.module';
import { TdGeneralDetailComponent } from 'app/entities/td-general/td-general-detail.component';
import { TdGeneral } from 'app/shared/model/td-general.model';

describe('Component Tests', () => {
  describe('TdGeneral Management Detail Component', () => {
    let comp: TdGeneralDetailComponent;
    let fixture: ComponentFixture<TdGeneralDetailComponent>;
    const route = ({ data: of({ tdGeneral: new TdGeneral(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RfcrfTestModule],
        declarations: [TdGeneralDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TdGeneralDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TdGeneralDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tdGeneral on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tdGeneral).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
