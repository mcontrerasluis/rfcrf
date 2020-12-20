import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITdGeneral, TdGeneral } from 'app/shared/model/servicios/td-general.model';
import { TdGeneralService } from './td-general.service';
import { ITdRegFront } from 'app/shared/model/servicios/td-reg-front.model';
import { TdRegFrontService } from 'app/entities/servicios/td-reg-front/td-reg-front.service';

@Component({
  selector: 'jhi-td-general-update',
  templateUrl: './td-general-update.component.html',
})
export class TdGeneralUpdateComponent implements OnInit {
  isSaving = false;
  tiposolicituds: ITdRegFront[] = [];

  editForm = this.fb.group({
    id: [],
    rfc: [null, [Validators.required]],
    fecha: [null, [Validators.required]],
    tipoSolicitudd: [null, [Validators.required]],
    folio: [null, [Validators.required]],
    estatus: [null, [Validators.required]],
    tipoSolicitudId: [],
  });

  constructor(
    protected tdGeneralService: TdGeneralService,
    protected tdRegFrontService: TdRegFrontService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tdGeneral }) => {
      if (!tdGeneral.id) {
        const today = moment().startOf('day');
        tdGeneral.fecha = today;
      }

      this.updateForm(tdGeneral);

      this.tdRegFrontService
        .query({ filter: 'general-is-null' })
        .pipe(
          map((res: HttpResponse<ITdRegFront[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ITdRegFront[]) => {
          if (!tdGeneral.tipoSolicitudId) {
            this.tiposolicituds = resBody;
          } else {
            this.tdRegFrontService
              .find(tdGeneral.tipoSolicitudId)
              .pipe(
                map((subRes: HttpResponse<ITdRegFront>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ITdRegFront[]) => (this.tiposolicituds = concatRes));
          }
        });
    });
  }

  updateForm(tdGeneral: ITdGeneral): void {
    this.editForm.patchValue({
      id: tdGeneral.id,
      rfc: tdGeneral.rfc,
      fecha: tdGeneral.fecha ? tdGeneral.fecha.format(DATE_TIME_FORMAT) : null,
      tipoSolicitudd: tdGeneral.tipoSolicitudd,
      folio: tdGeneral.folio,
      estatus: tdGeneral.estatus,
      tipoSolicitudId: tdGeneral.tipoSolicitudId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tdGeneral = this.createFromForm();
    if (tdGeneral.id !== undefined) {
      this.subscribeToSaveResponse(this.tdGeneralService.update(tdGeneral));
    } else {
      this.subscribeToSaveResponse(this.tdGeneralService.create(tdGeneral));
    }
  }

  private createFromForm(): ITdGeneral {
    return {
      ...new TdGeneral(),
      id: this.editForm.get(['id'])!.value,
      rfc: this.editForm.get(['rfc'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      tipoSolicitudd: this.editForm.get(['tipoSolicitudd'])!.value,
      folio: this.editForm.get(['folio'])!.value,
      estatus: this.editForm.get(['estatus'])!.value,
      tipoSolicitudId: this.editForm.get(['tipoSolicitudId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITdGeneral>>): void {
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

  trackById(index: number, item: ITdRegFront): any {
    return item.id;
  }
}
