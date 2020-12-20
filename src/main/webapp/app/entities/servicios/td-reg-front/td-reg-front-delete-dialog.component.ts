import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITdRegFront } from 'app/shared/model/servicios/td-reg-front.model';
import { TdRegFrontService } from './td-reg-front.service';

@Component({
  templateUrl: './td-reg-front-delete-dialog.component.html',
})
export class TdRegFrontDeleteDialogComponent {
  tdRegFront?: ITdRegFront;

  constructor(
    protected tdRegFrontService: TdRegFrontService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tdRegFrontService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tdRegFrontListModification');
      this.activeModal.close();
    });
  }
}
