import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITcTipoImp } from 'app/shared/model/servicios/tc-tipo-imp.model';

@Component({
  selector: 'jhi-tc-tipo-imp-detail',
  templateUrl: './tc-tipo-imp-detail.component.html',
})
export class TcTipoImpDetailComponent implements OnInit {
  tcTipoImp: ITcTipoImp | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tcTipoImp }) => (this.tcTipoImp = tcTipoImp));
  }

  previousState(): void {
    window.history.back();
  }
}
