import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITcEjerc } from 'app/shared/model/servicios/tc-ejerc.model';

@Component({
  selector: 'jhi-tc-ejerc-detail',
  templateUrl: './tc-ejerc-detail.component.html',
})
export class TcEjercDetailComponent implements OnInit {
  tcEjerc: ITcEjerc | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tcEjerc }) => (this.tcEjerc = tcEjerc));
  }

  previousState(): void {
    window.history.back();
  }
}
