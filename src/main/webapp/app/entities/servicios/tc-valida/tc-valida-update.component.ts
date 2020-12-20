import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITcValida, TcValida } from 'app/shared/model/servicios/tc-valida.model';
import { TcValidaService } from './tc-valida.service';

@Component({
  selector: 'jhi-tc-valida-update',
  templateUrl: './tc-valida-update.component.html',
})
export class TcValidaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    clave: [null, [Validators.required]],
    descripcion: [null, [Validators.required]],
    moral: [null, [Validators.required]],
    fisica: [null, [Validators.required]],
    isr: [null, [Validators.required]],
    iva: [null, [Validators.required]],
  });

  constructor(protected tcValidaService: TcValidaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tcValida }) => {
      this.updateForm(tcValida);
    });
  }

  updateForm(tcValida: ITcValida): void {
    this.editForm.patchValue({
      id: tcValida.id,
      clave: tcValida.clave,
      descripcion: tcValida.descripcion,
      moral: tcValida.moral,
      fisica: tcValida.fisica,
      isr: tcValida.isr,
      iva: tcValida.iva,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tcValida = this.createFromForm();
    if (tcValida.id !== undefined) {
      this.subscribeToSaveResponse(this.tcValidaService.update(tcValida));
    } else {
      this.subscribeToSaveResponse(this.tcValidaService.create(tcValida));
    }
  }

  private createFromForm(): ITcValida {
    return {
      ...new TcValida(),
      id: this.editForm.get(['id'])!.value,
      clave: this.editForm.get(['clave'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      moral: this.editForm.get(['moral'])!.value,
      fisica: this.editForm.get(['fisica'])!.value,
      isr: this.editForm.get(['isr'])!.value,
      iva: this.editForm.get(['iva'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITcValida>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
