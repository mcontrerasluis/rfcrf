import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITcTipoSol } from 'app/shared/model/servicios/tc-tipo-sol.model';

@Component({
  selector: 'jhi-tc-tipo-sol-detail',
  templateUrl: './tc-tipo-sol-detail.component.html',
})
export class TcTipoSolDetailComponent implements OnInit {
  tcTipoSol: ITcTipoSol | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tcTipoSol }) => (this.tcTipoSol = tcTipoSol));
  }

  previousState(): void {
    window.history.back();
  }
}
