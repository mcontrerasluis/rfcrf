import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITcManifes } from 'app/shared/model/servicios/tc-manifes.model';

@Component({
  selector: 'jhi-tc-manifes-detail',
  templateUrl: './tc-manifes-detail.component.html',
})
export class TcManifesDetailComponent implements OnInit {
  tcManifes: ITcManifes | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tcManifes }) => (this.tcManifes = tcManifes));
  }

  previousState(): void {
    window.history.back();
  }
}
