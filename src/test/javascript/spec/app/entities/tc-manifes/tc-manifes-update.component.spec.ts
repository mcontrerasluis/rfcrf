import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RfcrfTestModule } from '../../../test.module';
import { TcManifesUpdateComponent } from 'app/entities/tc-manifes/tc-manifes-update.component';
import { TcManifesService } from 'app/entities/tc-manifes/tc-manifes.service';
import { TcManifes } from 'app/shared/model/tc-manifes.model';

describe('Component Tests', () => {
  describe('TcManifes Management Update Component', () => {
    let comp: TcManifesUpdateComponent;
    let fixture: ComponentFixture<TcManifesUpdateComponent>;
    let service: TcManifesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RfcrfTestModule],
        declarations: [TcManifesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TcManifesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TcManifesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TcManifesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TcManifes(123);
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
        const entity = new TcManifes();
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
