import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RfcrfTestModule } from '../../../test.module';
import { TcValidaUpdateComponent } from 'app/entities/tc-valida/tc-valida-update.component';
import { TcValidaService } from 'app/entities/tc-valida/tc-valida.service';
import { TcValida } from 'app/shared/model/tc-valida.model';

describe('Component Tests', () => {
  describe('TcValida Management Update Component', () => {
    let comp: TcValidaUpdateComponent;
    let fixture: ComponentFixture<TcValidaUpdateComponent>;
    let service: TcValidaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RfcrfTestModule],
        declarations: [TcValidaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TcValidaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TcValidaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TcValidaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TcValida(123);
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
        const entity = new TcValida();
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
