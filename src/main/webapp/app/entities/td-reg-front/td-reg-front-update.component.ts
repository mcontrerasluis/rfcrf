import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITdRegFront, TdRegFront } from 'app/shared/model/td-reg-front.model';
import { TdRegFrontService } from './td-reg-front.service';
import { ITcTipoSol } from 'app/shared/model/servicios/tc-tipo-sol.model';
import { TcTipoSolService } from 'app/entities/servicios/tc-tipo-sol/tc-tipo-sol.service';
import { ITcTipoImp } from 'app/shared/model/servicios/tc-tipo-imp.model';
import { TcTipoImpService } from 'app/entities/servicios/tc-tipo-imp/tc-tipo-imp.service';
import { ITcEjerc } from 'app/shared/model/servicios/tc-ejerc.model';
import { TcEjercService } from 'app/entities/servicios/tc-ejerc/tc-ejerc.service';
import { ITcManifes } from 'app/shared/model/servicios/tc-manifes.model';
import { TcManifesService } from 'app/entities/servicios/tc-manifes/tc-manifes.service';
import { ITcValida } from 'app/shared/model/servicios/tc-valida.model';
import { TcValidaService } from 'app/entities/servicios/tc-valida/tc-valida.service';

type SelectableEntity = ITcTipoSol | ITcTipoImp | ITcEjerc | ITcManifes | ITcValida;

type SelectableManyToManyEntity = ITcManifes | ITcValida;

@Component({
  selector: 'jhi-td-reg-front-update',
  templateUrl: './td-reg-front-update.component.html',
})
export class TdRegFrontUpdateComponent implements OnInit {
  isSaving = false;
  tctiposols: ITcTipoSol[] = [];
  tctipoimps: ITcTipoImp[] = [];
  tcejercs: ITcEjerc[] = [];
  tcmanifes: ITcManifes[] = [];
  tcvalidas: ITcValida[] = [];

  editForm = this.fb.group({
    id: [],
    region: [null, [Validators.required]],
    domicilioRegion: [null, [Validators.required]],
    sucursalRegion: [null, [Validators.required]],
    tipoImpuestod: [],
    tipoSolicitudd: [],
    ejerciciod: [],
    estatus: [null, [Validators.required]],
    folio: [],
    rfc: [],
    fecha: [],
    usuario: [],
    tipoSolicitudId: [],
    tipoImpuestoId: [],
    ejercicioId: [],
    manifestacions: [],
    validacions: [],
  });

  constructor(
    protected tdRegFrontService: TdRegFrontService,
    protected tcTipoSolService: TcTipoSolService,
    protected tcTipoImpService: TcTipoImpService,
    protected tcEjercService: TcEjercService,
    protected tcManifesService: TcManifesService,
    protected tcValidaService: TcValidaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tdRegFront }) => {
      if (!tdRegFront.id) {
        const today = moment().startOf('day');
        tdRegFront.fecha = today;
      }

      this.updateForm(tdRegFront);

      this.tcTipoSolService.query().subscribe((res: HttpResponse<ITcTipoSol[]>) => (this.tctiposols = res.body || []));

      this.tcTipoImpService.query().subscribe((res: HttpResponse<ITcTipoImp[]>) => (this.tctipoimps = res.body || []));

      this.tcEjercService.query().subscribe((res: HttpResponse<ITcEjerc[]>) => (this.tcejercs = res.body || []));

      this.tcManifesService.query().subscribe((res: HttpResponse<ITcManifes[]>) => (this.tcmanifes = res.body || []));

      this.tcValidaService.query().subscribe((res: HttpResponse<ITcValida[]>) => (this.tcvalidas = res.body || []));
    });
  }

  updateForm(tdRegFront: ITdRegFront): void {
    this.editForm.patchValue({
      id: tdRegFront.id,
      region: tdRegFront.region,
      domicilioRegion: tdRegFront.domicilioRegion,
      sucursalRegion: tdRegFront.sucursalRegion,
      tipoImpuestod: tdRegFront.tipoImpuestod,
      tipoSolicitudd: tdRegFront.tipoSolicitudd,
      ejerciciod: tdRegFront.ejerciciod,
      estatus: tdRegFront.estatus,
      folio: tdRegFront.folio,
      rfc: tdRegFront.rfc,
      fecha: tdRegFront.fecha ? tdRegFront.fecha.format(DATE_TIME_FORMAT) : null,
      usuario: tdRegFront.usuario,
      tipoSolicitudId: tdRegFront.tipoSolicitudId,
      tipoImpuestoId: tdRegFront.tipoImpuestoId,
      ejercicioId: tdRegFront.ejercicioId,
      manifestacions: tdRegFront.manifestacions,
      validacions: tdRegFront.validacions,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tdRegFront = this.createFromForm();
    if (tdRegFront.id !== undefined) {
      this.subscribeToSaveResponse(this.tdRegFrontService.update(tdRegFront));
    } else {
      this.subscribeToSaveResponse(this.tdRegFrontService.create(tdRegFront));
    }
  }

  private createFromForm(): ITdRegFront {
    return {
      ...new TdRegFront(),
      id: this.editForm.get(['id'])!.value,
      region: this.editForm.get(['region'])!.value,
      domicilioRegion: this.editForm.get(['domicilioRegion'])!.value,
      sucursalRegion: this.editForm.get(['sucursalRegion'])!.value,
      tipoImpuestod: this.editForm.get(['tipoImpuestod'])!.value,
      tipoSolicitudd: this.editForm.get(['tipoSolicitudd'])!.value,
      ejerciciod: this.editForm.get(['ejerciciod'])!.value,
      estatus: this.editForm.get(['estatus'])!.value,
      folio: this.editForm.get(['folio'])!.value,
      rfc: this.editForm.get(['rfc'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      usuario: this.editForm.get(['usuario'])!.value,
      tipoSolicitudId: this.editForm.get(['tipoSolicitudId'])!.value,
      tipoImpuestoId: this.editForm.get(['tipoImpuestoId'])!.value,
      ejercicioId: this.editForm.get(['ejercicioId'])!.value,
      manifestacions: this.editForm.get(['manifestacions'])!.value,
      validacions: this.editForm.get(['validacions'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITdRegFront>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: SelectableManyToManyEntity[], option: SelectableManyToManyEntity): SelectableManyToManyEntity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
