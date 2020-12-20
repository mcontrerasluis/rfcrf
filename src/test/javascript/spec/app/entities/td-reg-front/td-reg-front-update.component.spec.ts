import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RfcrfTestModule } from '../../../test.module';
import { TdRegFrontUpdateComponent } from 'app/entities/td-reg-front/td-reg-front-update.component';
import { TdRegFrontService } from 'app/entities/td-reg-front/td-reg-front.service';
import { TdRegFront } from 'app/shared/model/td-reg-front.model';

describe('Component Tests', () => {
  describe('TdRegFront Management Update Component', () => {
    let comp: TdRegFrontUpdateComponent;
    let fixture: ComponentFixture<TdRegFrontUpdateComponent>;
    let service: TdRegFrontService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RfcrfTestModule],
        declarations: [TdRegFrontUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TdRegFrontUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TdRegFrontUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TdRegFrontService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TdRegFront(123);
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
        const entity = new TdRegFront();
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
