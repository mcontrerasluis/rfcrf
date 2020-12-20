import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITcManifes, TcManifes } from 'app/shared/model/servicios/tc-manifes.model';
import { TcManifesService } from './tc-manifes.service';

@Component({
  selector: 'jhi-tc-manifes-update',
  templateUrl: './tc-manifes-update.component.html',
})
export class TcManifesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    clave: [null, [Validators.required]],
    descripcion: [null, [Validators.required]],
    moral: [null, [Validators.required]],
    fisica: [null, [Validators.required]],
    isr: [null, [Validators.required]],
    iva: [null, [Validators.required]],
    rfnorte: [null, [Validators.required]],
    rfsur: [null, [Validators.required]],
    s01: [null, [Validators.required]],
    s02: [null, [Validators.required]],
    s03: [null, [Validators.required]],
    s04: [null, [Validators.required]],
  });

  constructor(protected tcManifesService: TcManifesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tcManifes }) => {
      this.updateForm(tcManifes);
    });
  }

  updateForm(tcManifes: ITcManifes): void {
    this.editForm.patchValue({
      id: tcManifes.id,
      clave: tcManifes.clave,
      descripcion: tcManifes.descripcion,
      moral: tcManifes.moral,
      fisica: tcManifes.fisica,
      isr: tcManifes.isr,
      iva: tcManifes.iva,
      rfnorte: tcManifes.rfnorte,
      rfsur: tcManifes.rfsur,
      s01: tcManifes.s01,
      s02: tcManifes.s02,
      s03: tcManifes.s03,
      s04: tcManifes.s04,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tcManifes = this.createFromForm();
    if (tcManifes.id !== undefined) {
      this.subscribeToSaveResponse(this.tcManifesService.update(tcManifes));
    } else {
      this.subscribeToSaveResponse(this.tcManifesService.create(tcManifes));
    }
  }

  private createFromForm(): ITcManifes {
    return {
      ...new TcManifes(),
      id: this.editForm.get(['id'])!.value,
      clave: this.editForm.get(['clave'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      moral: this.editForm.get(['moral'])!.value,
      fisica: this.editForm.get(['fisica'])!.value,
      isr: this.editForm.get(['isr'])!.value,
      iva: this.editForm.get(['iva'])!.value,
      rfnorte: this.editForm.get(['rfnorte'])!.value,
      rfsur: this.editForm.get(['rfsur'])!.value,
      s01: this.editForm.get(['s01'])!.value,
      s02: this.editForm.get(['s02'])!.value,
      s03: this.editForm.get(['s03'])!.value,
      s04: this.editForm.get(['s04'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITcManifes>>): void {
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
