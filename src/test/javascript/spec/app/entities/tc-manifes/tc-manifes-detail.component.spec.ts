import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RfcrfTestModule } from '../../../test.module';
import { TcManifesDetailComponent } from 'app/entities/tc-manifes/tc-manifes-detail.component';
import { TcManifes } from 'app/shared/model/tc-manifes.model';

describe('Component Tests', () => {
  describe('TcManifes Management Detail Component', () => {
    let comp: TcManifesDetailComponent;
    let fixture: ComponentFixture<TcManifesDetailComponent>;
    const route = ({ data: of({ tcManifes: new TcManifes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RfcrfTestModule],
        declarations: [TcManifesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TcManifesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TcManifesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tcManifes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tcManifes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
