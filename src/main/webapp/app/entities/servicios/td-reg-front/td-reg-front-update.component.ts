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
import { MenuItem, MessageService, ConfirmationService } from 'primeng/api';
import { SelectItem } from 'primeng/api';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';

type SelectableEntity = ITcTipoSol | ITcTipoImp | ITcEjerc | ITcManifes | ITcValida;

type SelectableManyToManyEntity = ITcManifes | ITcValida;

@Component({
  selector: 'jhi-td-reg-front-update',
  templateUrl: './td-reg-front-update.component.html',
  providers: [MessageService, ConfirmationService],
})
export class TdRegFrontUpdateComponent implements OnInit {
  account: Account | null = null;
  isSaving = false;
  tctiposols: ITcTipoSol[] = [];
  tctiposolsS: ITcTipoSol[] = [];
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
  domicilioDesactiva = false;
  activaAnterior = true;
  control: string[]=[];

  public src: Blob;
  public srcS: any;

  regionesF: SelectItem[];
  selectedDrop: SelectItem;
  selectedRegiones: SelectItem;
  valToggle = false;  

  editForm = this.fb.group({
    id: [],
    region: [null, [Validators.required]],
    domicilioRegion: [null,[Validators.requiredTrue]],
    sucursalRegion: [null,[Validators.requiredTrue]],
    tipoImpuestod: [],
    tipoSolicitudd: [],
    ejerciciod: [],
    tipoSolicitudId: [null,[Validators.required]],
    tipoImpuestoId: [null, [Validators.required]],
    ejercicioId: [null, [Validators.required]],
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
    public messageService: MessageService,
    public confirmationService: ConfirmationService,
    private fb: FormBuilder,
    private http: HttpClient,
    private accountService: AccountService, 
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tdRegFront }) => {
      this.updateForm(tdRegFront);      

      this.tcTipoSolService.query().subscribe((res: HttpResponse<ITcTipoSol[]>) => (this.tctiposols = res.body || []));

      this.tcTipoImpService.query().subscribe((res: HttpResponse<ITcTipoImp[]>) => (this.tctipoimps = res.body || []));

      this.tcEjercService.query().subscribe((res: HttpResponse<ITcEjerc[]>) => (this.tcejercs = res.body || []));      

      this.tcManifesService.query().subscribe((res: HttpResponse<ITcManifes[]>) => (this.tcmanifes = res.body || []));

      this.tcValidaService.query().subscribe((res: HttpResponse<ITcValida[]>) => (this.tcvalidas = res.body || []));

      this.accountService.identity().subscribe(account => (this.account = account));    
    });

    this.regionesF = [
      { label: 'Región Frontera Norte', value: 'Región Frontera Norte' },
      { label: 'Región Frontera Sur', value: 'Región Frontera Sur' },      
    ];

    this.items = [
      {
        label: 'General',
        command: (event: any) => {
          this.activeIndex = 0;
          this.general = true;
          this.manifiesta = false;
          this.vistaPrevia = false;
          this.activaAnterior= true;          
          this.editForm.get(['manifestacions']).clearValidators();
        },
      },
      {
        label: 'Manifestaciones',
        command: (event: any) => {

          if(this.editForm.valid){
          
            this.activeIndex = 1;
            this.general = false;
            this.manifiesta = true;
            this.vistaPrevia = false;
            this.activaAnterior= false;
            
            this.cargaManifestaciones();
            // this.editForm.get(['manifestacions']).setValidators(Validators.requiredTrue)

            return;
          
          }else if(!this.editForm.valid){
            this.messageService.add({ severity: 'error', summary: 'Información Incompleta', detail: 'Se deben capturar todos los campos para continuar' });
            this.activeIndex = 0;
            
            return;
          }  
          
          
        },
      },
      {
        label: 'Vista Previa',
        command: (event: any) => {

          if(this.editForm.valid){
          
            this.activeIndex = 2;
            this.general = false;
            this.cargaAcuse()
            this.manifiesta = false;
            this.vistaPrevia = true;
            this.activaAnterior= false;

            return;

          }else if(!this.editForm.valid){
            this.messageService.add({ severity: 'error', summary: 'Información Incompleta', detail: 'Se deben capturar todos los campos para continuar' });
            this.activeIndex = 1;
            
            return;
          }  
        },
      },
    ];
  }

  filtraSolicitud(event:any):void {    
    
    let cla = '';
    const impuesto = this.tctipoimps.find(obj =>{
      return obj.id===this.editForm.get(['tipoImpuestoId']).value
    })

    if(impuesto.clave === '01'){
      
      cla = 'isr'
    }else if(impuesto.clave === '02'){
      cla = 'iva'
    }
    
    this.tctiposolsS = this.tctiposols.filter(function(v, i) {
      return ((v[cla] === 1));
    })    

  }

  private cargaManifestaciones():void{
    let cla = '';
    let sol = '';
    let reg = '';    
    let solicitud = null;
    let impuesto = null;
    let tipo = '' ;

    
    solicitud = this.tctiposols.find(obj =>{
      return obj.id===this.editForm.get(['tipoSolicitudId']).value
    })

    impuesto = this.tctipoimps.find(obj =>{
      return obj.id===this.editForm.get(['tipoImpuestoId']).value
    })
    

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
    
    if(this.account.tipoContribuyente === 'F'){
      tipo = 'fisica';
    }else{
      tipo = 'moral';
    }    

    this.tcmanifesS = this.tcmanifes.filter(function(v, i) {
      return ((v[tipo] ===1 && v[cla] === 1 && v[sol] === 1 && v[reg]===1 ));
    })
  
  }

  nextPage():void {

    if (this.activeIndex === 0) {

      if(this.editForm.valid){
        this.activeIndex = 1;
        this.general = false;
        this.manifiesta = true; 
        this.vistaPrevia = false; 
        this.activaAnterior= false;
        this.cargaManifestaciones();  
        // this.editForm.get(['manifestacions']).setValidators(Validators.requiredTrue)
        
        return;

      }else if(!this.editForm.valid){
        this.messageService.add({ severity: 'error', summary: 'Información Incompleta', detail: 'Se deben capturar todos los campos para continuar' });
        
        return;
      }  

        
    }

    if (this.activeIndex === 1) {

      console.log(this.control.filter((n, i) => this.control.indexOf(n) === i));
      console.log(this.tcmanifesS);

      if((this.control.filter((n, i) => this.control.indexOf(n) === i)).length >= this.tcmanifesS.length){
        this.activeIndex = 2;
        this.general = false;
        this.manifiesta = false;
        this.vistaPrevia = true;
        this.cargaAcuse();
        return;
      }else {
        this.messageService.add({ severity: 'error', summary: 'Información Incompleta', detail: 'Se deben contestar todas las manifestaciones para continuar' });
      }    

      return;
  } 

    
}

  seleccionaManifestacion(e: any, clave:any) {

    this.tcmanifes.find(item => item.clave === clave).activa = false;

    this.control.push(clave);

    if(clave === 'M01'){
      this.tcmanifes.find(item => item.clave === 'M02').activa = e.checked;      
      this.control.push("M02");      
      return;
    }else if(clave==='M02'){
      this.tcmanifes.find(item => item.clave === 'M01').activa = e.checked;
      this.control.push("M01");
      return;
    }
    if(clave === 'M03'){
      this.tcmanifes.find(item => item.clave === 'M04').activa = e.checked;
      this.control.push("M04");
      return;
    }else if(clave==='M04'){
      this.tcmanifes.find(item => item.clave === 'M03').activa = e.checked;
      this.control.push("M03");
      return;
    }
    if(clave === 'M05'){
      this.tcmanifes.find(item => item.clave === 'M06').activa = e.checked;
      this.control.push("M06");
      return;
    }else if(clave==='M06'){
      this.tcmanifes.find(item => item.clave === 'M05').activa = e.checked;
      this.control.push("M05");
      return;
    }
    if(clave === 'M11'){
      this.tcmanifes.find(item => item.clave === 'M12').activa = e.checked;
      this.control.push("M12");
      return;
    }else if(clave==='M12'){
      this.tcmanifes.find(item => item.clave === 'M11').activa = e.checked;
      this.control.push("M11");
      return;
    }   
    
  }

  previousPage():void {
 
    if (this.activeIndex === 1) {
          
      this.activeIndex = 0;
      this.general = true;
      this.manifiesta = false;    
      this.vistaPrevia = false;
      this.activaAnterior= true;
      this.editForm.get(['manifestacions']).clearValidators();

      return;
  }

  if (this.activeIndex === 2) {
        
      this.activeIndex = 1;
      this.general = false;
      this.manifiesta = true;
      this.vistaPrevia = false;
      this.activaAnterior= false;

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
    
    if (this.editForm.get(['domicilioRegion']).value === true) {
      this.sucursalDesactiva = true;
      this.editForm.get(['sucursalRegion']).clearValidators();
      this.editForm.get(['sucursalRegion']).setValue(false);

      
      return;
    }
    if (this.editForm.get(['domicilioRegion']).value === false) {
      this.editForm.get(['sucursalRegion']).setValidators(Validators.requiredTrue)
      this.sucursalDesactiva = false;      
    }
  }

  desactivaDomicilio():void {
    
    if (this.editForm.get(['sucursalRegion']).value === true) {
      this.domicilioDesactiva = true;
      this.editForm.get(['domicilioRegion']).clearValidators();
      this.editForm.get(['domicilioRegion']).setValue(false);

      
      return;
    }
    if (this.editForm.get(['sucursalRegion']).value === false) {
      this.editForm.get(['domicilioRegion']).setValidators(Validators.requiredTrue)
      this.domicilioDesactiva = false;      
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
      // manifestacions: this.tcmanifesS,
      manifestacions: this.tcmanifes.filter(function(v, i) {
        return ((v['activa'] === false));
      }),
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
    this.messageService.add({ severity: 'success', summary: 'Información Registrada', detail: 'La información se ha registrado de manera Exitosa' });
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

  public cargaAcuse(): void {
    const tdRegFront = this.createFromForm();
    this.http.post(this.tdRegFrontService.acuse(), tdRegFront, { responseType: 'text', headers: {'Accept': 'text/plain'} }).subscribe(
    // this.tdRegFrontService.acuse(tdRegFront).subscribe(
      (response) => {
      // const blob = new Blob([response], { type: 'application/octet-stream' });
      // const blob = new Blob([response], { type: 'application/octet-stream' });
      // const blob = base64StringToBlob(response, 'text/plain')
      this.srcS = response;
      // this.src = blob;
      console.log(response)      
      console.log(this.srcS)      
  },
  e => { console.log(e); })    
  }  
}
