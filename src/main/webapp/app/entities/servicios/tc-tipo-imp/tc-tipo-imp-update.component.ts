import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITcTipoImp, TcTipoImp } from 'app/shared/model/servicios/tc-tipo-imp.model';
import { TcTipoImpService } from './tc-tipo-imp.service';

@Component({
  selector: 'jhi-tc-tipo-imp-update',
  templateUrl: './tc-tipo-imp-update.component.html',
})
export class TcTipoImpUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    clave: [null, [Validators.required]],
    descripcion: [null, [Validators.required]],
    fechaInicio: [],
    fechaFin: [],
  });

  constructor(protected tcTipoImpService: TcTipoImpService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tcTipoImp }) => {
      if (!tcTipoImp.id) {
        const today = moment().startOf('day');
        tcTipoImp.fechaInicio = today;
        tcTipoImp.fechaFin = today;
      }

      this.updateForm(tcTipoImp);
    });
  }

  updateForm(tcTipoImp: ITcTipoImp): void {
    this.editForm.patchValue({
      id: tcTipoImp.id,
      clave: tcTipoImp.clave,
      descripcion: tcTipoImp.descripcion,
      fechaInicio: tcTipoImp.fechaInicio ? tcTipoImp.fechaInicio.format(DATE_TIME_FORMAT) : null,
      fechaFin: tcTipoImp.fechaFin ? tcTipoImp.fechaFin.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tcTipoImp = this.createFromForm();
    if (tcTipoImp.id !== undefined) {
      this.subscribeToSaveResponse(this.tcTipoImpService.update(tcTipoImp));
    } else {
      this.subscribeToSaveResponse(this.tcTipoImpService.create(tcTipoImp));
    }
  }

  private createFromForm(): ITcTipoImp {
    return {
      ...new TcTipoImp(),
      id: this.editForm.get(['id'])!.value,
      clave: this.editForm.get(['clave'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      fechaInicio: this.editForm.get(['fechaInicio'])!.value
        ? moment(this.editForm.get(['fechaInicio'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fechaFin: this.editForm.get(['fechaFin'])!.value ? moment(this.editForm.get(['fechaFin'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITcTipoImp>>): void {
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
