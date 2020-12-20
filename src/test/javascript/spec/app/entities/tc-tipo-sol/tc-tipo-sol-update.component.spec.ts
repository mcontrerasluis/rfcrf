import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RfcrfTestModule } from '../../../test.module';
import { TcTipoSolUpdateComponent } from 'app/entities/tc-tipo-sol/tc-tipo-sol-update.component';
import { TcTipoSolService } from 'app/entities/tc-tipo-sol/tc-tipo-sol.service';
import { TcTipoSol } from 'app/shared/model/tc-tipo-sol.model';

describe('Component Tests', () => {
  describe('TcTipoSol Management Update Component', () => {
    let comp: TcTipoSolUpdateComponent;
    let fixture: ComponentFixture<TcTipoSolUpdateComponent>;
    let service: TcTipoSolService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RfcrfTestModule],
        declarations: [TcTipoSolUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TcTipoSolUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TcTipoSolUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TcTipoSolService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TcTipoSol(123);
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
        const entity = new TcTipoSol();
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
