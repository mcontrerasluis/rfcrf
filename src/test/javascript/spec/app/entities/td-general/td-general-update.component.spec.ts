import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RfcrfTestModule } from '../../../test.module';
import { TdGeneralUpdateComponent } from 'app/entities/td-general/td-general-update.component';
import { TdGeneralService } from 'app/entities/td-general/td-general.service';
import { TdGeneral } from 'app/shared/model/td-general.model';

describe('Component Tests', () => {
  describe('TdGeneral Management Update Component', () => {
    let comp: TdGeneralUpdateComponent;
    let fixture: ComponentFixture<TdGeneralUpdateComponent>;
    let service: TdGeneralService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RfcrfTestModule],
        declarations: [TdGeneralUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TdGeneralUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TdGeneralUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TdGeneralService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TdGeneral(123);
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
        const entity = new TdGeneral();
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
