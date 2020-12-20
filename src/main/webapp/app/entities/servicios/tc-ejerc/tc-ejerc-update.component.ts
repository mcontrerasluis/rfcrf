import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITcEjerc, TcEjerc } from 'app/shared/model/servicios/tc-ejerc.model';
import { TcEjercService } from './tc-ejerc.service';

@Component({
  selector: 'jhi-tc-ejerc-update',
  templateUrl: './tc-ejerc-update.component.html',
})
export class TcEjercUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    clave: [null, [Validators.required]],
    valor: [null, [Validators.required]],
    fechaInicio: [],
    fechaFin: [],
  });

  constructor(protected tcEjercService: TcEjercService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tcEjerc }) => {
      if (!tcEjerc.id) {
        const today = moment().startOf('day');
        tcEjerc.fechaInicio = today;
        tcEjerc.fechaFin = today;
      }

      this.updateForm(tcEjerc);
    });
  }

  updateForm(tcEjerc: ITcEjerc): void {
    this.editForm.patchValue({
      id: tcEjerc.id,
      clave: tcEjerc.clave,
      valor: tcEjerc.valor,
      fechaInicio: tcEjerc.fechaInicio ? tcEjerc.fechaInicio.format(DATE_TIME_FORMAT) : null,
      fechaFin: tcEjerc.fechaFin ? tcEjerc.fechaFin.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tcEjerc = this.createFromForm();
    if (tcEjerc.id !== undefined) {
      this.subscribeToSaveResponse(this.tcEjercService.update(tcEjerc));
    } else {
      this.subscribeToSaveResponse(this.tcEjercService.create(tcEjerc));
    }
  }

  private createFromForm(): ITcEjerc {
    return {
      ...new TcEjerc(),
      id: this.editForm.get(['id'])!.value,
      clave: this.editForm.get(['clave'])!.value,
      valor: this.editForm.get(['valor'])!.value,
      fechaInicio: this.editForm.get(['fechaInicio'])!.value
        ? moment(this.editForm.get(['fechaInicio'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fechaFin: this.editForm.get(['fechaFin'])!.value ? moment(this.editForm.get(['fechaFin'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITcEjerc>>): void {
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
