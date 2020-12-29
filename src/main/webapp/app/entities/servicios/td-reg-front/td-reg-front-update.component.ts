/* eslint-disable no-console */
import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpClient } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITdRegFront, TdRegFront } from 'app/shared/model/servicios/td-reg-front.model';
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
import { MenuItem, MessageService } from 'primeng/api';
import { SelectItem } from 'primeng/api';
import { arrayBufferToBlob } from 'blob-util'

type SelectableEntity = ITcTipoSol | ITcTipoImp | ITcEjerc | ITcManifes | ITcValida;

type SelectableManyToManyEntity = ITcManifes | ITcValida;

@Component({
  selector: 'jhi-td-reg-front-update',
  templateUrl: './td-reg-front-update.component.html',
  providers: [MessageService],
})
export class TdRegFrontUpdateComponent implements OnInit {
  isSaving = false;
  tctiposols: ITcTipoSol[] = [];
  tctipoimps: ITcTipoImp[] = [];
  tcejercs: ITcEjerc[] = [];
  tcmanifes: ITcManifes[] = [];
  tcmanifesS: ITcManifes[] = [];
  tcvalidas: ITcValida[] = [];
  items: MenuItem[];
  activeIndex = 0;
  general = true;
  manifiesta = false;
  vistaPrevia = false;
  sucursalDesactiva = false;
  pdfSrc = "http://localhost:8080/RFCSolZnFr/api/getpdf"

  public src: Blob;

  regionesF: SelectItem[];
  selectedDrop: SelectItem;
  selectedRegiones: SelectItem;
  valToggle = false;  

  editForm = this.fb.group({
    id: [],
    region: [null, [Validators.required]],
    domicilioRegion: [null,[Validators.required]],
    sucursalRegion: [],
    tipoImpuestod: [],
    tipoSolicitudd: [],
    ejerciciod: [],
    tipoSolicitudId: [null,[Validators.required]],
    tipoImpuestoId: [null, [Validators.required]],
    ejercicioId: [null, [Validators.required]],
    manifestacions: [null, [Validators.required]],
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
    public messageService: MessageService,
    private fb: FormBuilder,
    private http: HttpClient, 
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tdRegFront }) => {
      this.updateForm(tdRegFront);

      this.tcTipoSolService.query().subscribe((res: HttpResponse<ITcTipoSol[]>) => (this.tctiposols = res.body || []));

      this.tcTipoImpService.query().subscribe((res: HttpResponse<ITcTipoImp[]>) => (this.tctipoimps = res.body || []));

      this.tcEjercService.query().subscribe((res: HttpResponse<ITcEjerc[]>) => (this.tcejercs = res.body || []));      

      this.tcManifesService.query().subscribe((res: HttpResponse<ITcManifes[]>) => (this.tcmanifes = res.body || []));

      this.tcValidaService.query().subscribe((res: HttpResponse<ITcValida[]>) => (this.tcvalidas = res.body || []));
    });

    this.regionesF = [
      { label: 'Región Frontera Sur', value: 'Región Frontera Sur' },
      { label: 'Región Frontera Norte', value: 'Región Frontera Norte' },
    ];

    this.items = [
      {
        label: 'General',
        command: (event: any) => {
          this.activeIndex = 0;
          this.general = true;
          this.manifiesta = false;
          this.vistaPrevia = false;
          this.loadLargeFile();
          this.messageService.add({ severity: 'info', summary: 'First Step', detail: event.item.label });
        },
      },
      {
        label: 'Manifestaciones',
        command: (event: any) => {
          this.activeIndex = 1;
          this.general = false;
          this.manifiesta = true;
          this.vistaPrevia = false;
          
          this.cargaManifestaciones();
          
        },
      },
      {
        label: 'Vista Previa',
        command: (event: any) => {
          this.activeIndex = 2;
          this.general = false;
          this.manifiesta = false;
          this.vistaPrevia = true;
          
        },
      },
    ];
  }

  private cargaManifestaciones():void{
    let cla = '';
    let sol = '';
    let reg = '';    
    let solicitud = null;
    let impuesto = null;
    let ciclo = null;

    
    solicitud = this.tctiposols.find(obj =>{
      return obj.id===this.editForm.get(['tipoSolicitudId']).value
    })

    impuesto = this.tctipoimps.find(obj =>{
      return obj.id===this.editForm.get(['tipoImpuestoId']).value
    })

    ciclo = this.tcejercs.find(obj =>{
      return obj.id===this.editForm.get(['ejercicioId']).value
    })

    console.log(solicitud);
    console.log(impuesto);
    console.log(ciclo);

    if(impuesto.clave === '01'){
      cla = 'isr'
    }else if(impuesto.clave === '02'){
      cla = 'iva'
    }

    if(solicitud.clave === 'S01'){
      sol = 's01';
    }else if(solicitud.clave === 'S02'){
      sol = 's02';
    }else if(solicitud.clave === 'S03'){
      sol = 's03';
    }else if(solicitud.clave === 'S04'){
      sol = 's04';
    }
    
    if(this.editForm.get(['region'])!.value==='Región Frontera Sur'){
      reg ='rfsur';
    }else{
      reg ='rfnorte'
    }

    console.log(cla + sol + reg)

    this.tcmanifesS = this.tcmanifes.filter(function(v, i) {
      return ((v[cla] === 1 && v[sol] === 1 && v[reg]===1) );
    })
  
  }

  nextPage():void {

    if (this.activeIndex === 0) {
          
        this.activeIndex = 1;
        this.general = false;
        this.manifiesta = true; 
        this.vistaPrevia = false; 
        this.cargaManifestaciones();  

        

        return;
    }

    if (this.activeIndex === 1) {
          
        this.activeIndex = 2;
        this.general = false;
        this.manifiesta = false;
        this.vistaPrevia = true;

      return;
  } 

    
}

  previousPage():void {
 
    if (this.activeIndex === 1) {
          
      this.activeIndex = 0;
      this.general = true;
      this.manifiesta = false;    
      this.vistaPrevia = false;

      return;
  }

  if (this.activeIndex === 2) {
        
      this.activeIndex = 1;
      this.general = false;
      this.manifiesta = true;
      this.vistaPrevia = false;

    return;
}

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

  desactivaSucursal():void {
    // eslint-disable-next-line no-console
    console.log(this.editForm.get(['domicilioRegion']).value);    
    
    if (this.sucursalDesactiva) {
      this.sucursalDesactiva = false;
      return;
    }
    if (!this.sucursalDesactiva) {
      this.sucursalDesactiva = true;
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
      tipoSolicitudId: this.editForm.get(['tipoSolicitudId'])!.value,
      tipoImpuestoId: this.editForm.get(['tipoImpuestoId'])!.value,
      ejercicioId: this.editForm.get(['ejercicioId'])!.value,
      manifestacions: this.tcmanifesS,
      validacions: this.editForm.get(['validacions'])!.value,
      estatus: 'Recibido',
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

  public loadLargeFile(): void {
    this.http
      .get(
        'http://localhost:8080/RFCSolZnFr/content/css/reportExample.pdf',
        { responseType: 'blob' }
      )
      .subscribe((res) =>  this.src = res)                  
  }

  
}
