import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RfcrfTestModule } from '../../../test.module';
import { TcTipoImpUpdateComponent } from 'app/entities/tc-tipo-imp/tc-tipo-imp-update.component';
import { TcTipoImpService } from 'app/entities/tc-tipo-imp/tc-tipo-imp.service';
import { TcTipoImp } from 'app/shared/model/tc-tipo-imp.model';

describe('Component Tests', () => {
  describe('TcTipoImp Management Update Component', () => {
    let comp: TcTipoImpUpdateComponent;
    let fixture: ComponentFixture<TcTipoImpUpdateComponent>;
    let service: TcTipoImpService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RfcrfTestModule],
        declarations: [TcTipoImpUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TcTipoImpUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TcTipoImpUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TcTipoImpService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TcTipoImp(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TcTipoImp();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
