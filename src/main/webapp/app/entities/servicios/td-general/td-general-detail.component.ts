import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITdGeneral } from 'app/shared/model/servicios/td-general.model';

@Component({
  selector: 'jhi-td-general-detail',
  templateUrl: './td-general-detail.component.html',
})
export class TdGeneralDetailComponent implements OnInit {
  tdGeneral: ITdGeneral | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tdGeneral }) => (this.tdGeneral = tdGeneral));
  }

  previousState(): void {
    window.history.back();
  }
}
