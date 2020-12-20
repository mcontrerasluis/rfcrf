import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITcValida } from 'app/shared/model/servicios/tc-valida.model';

@Component({
  selector: 'jhi-tc-valida-detail',
  templateUrl: './tc-valida-detail.component.html',
})
export class TcValidaDetailComponent implements OnInit {
  tcValida: ITcValida | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tcValida }) => (this.tcValida = tcValida));
  }

  previousState(): void {
    window.history.back();
  }
}
