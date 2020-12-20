import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITcTipoSol, TcTipoSol } from 'app/shared/model/tc-tipo-sol.model';
import { TcTipoSolService } from './tc-tipo-sol.service';

@Component({
  selector: 'jhi-tc-tipo-sol-update',
  templateUrl: './tc-tipo-sol-update.component.html',
})
export class TcTipoSolUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    clave: [null, [Validators.required]],
    descripcion: [null, [Validators.required]],
    isr: [null, [Validators.required, Validators.min(0), Validators.max(1)]],
    iva: [null, [Validators.required, Validators.min(0), Validators.max(1)]],
    efirma: [null, [Validators.required, Validators.min(0), Validators.max(1)]],
    fechaInicio: [],
    fechaFin: [],
    usuario: [],
  });

  constructor(protected tcTipoSolService: TcTipoSolService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tcTipoSol }) => {
      if (!tcTipoSol.id) {
        const today = moment().startOf('day');
        tcTipoSol.fechaInicio = today;
        tcTipoSol.fechaFin = today;
      }

      this.updateForm(tcTipoSol);
    });
  }

  updateForm(tcTipoSol: ITcTipoSol): void {
    this.editForm.patchValue({
      id: tcTipoSol.id,
      clave: tcTipoSol.clave,
      descripcion: tcTipoSol.descripcion,
      isr: tcTipoSol.isr,
      iva: tcTipoSol.iva,
      efirma: tcTipoSol.efirma,
      fechaInicio: tcTipoSol.fechaInicio ? tcTipoSol.fechaInicio.format(DATE_TIME_FORMAT) : null,
      fechaFin: tcTipoSol.fechaFin ? tcTipoSol.fechaFin.format(DATE_TIME_FORMAT) : null,
      usuario: tcTipoSol.usuario,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tcTipoSol = this.createFromForm();
    if (tcTipoSol.id !== undefined) {
      this.subscribeToSaveResponse(this.tcTipoSolService.update(tcTipoSol));
    } else {
      this.subscribeToSaveResponse(this.tcTipoSolService.create(tcTipoSol));
    }
  }

  private createFromForm(): ITcTipoSol {
    return {
      ...new TcTipoSol(),
      id: this.editForm.get(['id'])!.value,
      clave: this.editForm.get(['clave'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      isr: this.editForm.get(['isr'])!.value,
      iva: this.editForm.get(['iva'])!.value,
      efirma: this.editForm.get(['efirma'])!.value,
      fechaInicio: this.editForm.get(['fechaInicio'])!.value
        ? moment(this.editForm.get(['fechaInicio'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fechaFin: this.editForm.get(['fechaFin'])!.value ? moment(this.editForm.get(['fechaFin'])!.value, DATE_TIME_FORMAT) : undefined,
      usuario: this.editForm.get(['usuario'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITcTipoSol>>): void {
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
