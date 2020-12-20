import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RfcrfTestModule } from '../../../test.module';
import { TcEjercUpdateComponent } from 'app/entities/tc-ejerc/tc-ejerc-update.component';
import { TcEjercService } from 'app/entities/tc-ejerc/tc-ejerc.service';
import { TcEjerc } from 'app/shared/model/tc-ejerc.model';

describe('Component Tests', () => {
  describe('TcEjerc Management Update Component', () => {
    let comp: TcEjercUpdateComponent;
    let fixture: ComponentFixture<TcEjercUpdateComponent>;
    let service: TcEjercService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RfcrfTestModule],
        declarations: [TcEjercUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TcEjercUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TcEjercUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TcEjercService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TcEjerc(123);
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
        const entity = new TcEjerc();
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
