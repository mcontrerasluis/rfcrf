import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITdRegFront } from 'app/shared/model/servicios/td-reg-front.model';

@Component({
  selector: 'jhi-td-reg-front-detail',
  templateUrl: './td-reg-front-detail.component.html',
})
export class TdRegFrontDetailComponent implements OnInit {
  tdRegFront: ITdRegFront | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tdRegFront }) => (this.tdRegFront = tdRegFront));
  }

  previousState(): void {
    window.history.back();
  }
}
