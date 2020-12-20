import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITdGeneral } from 'app/shared/model/servicios/td-general.model';
import { TdGeneralService } from './td-general.service';

@Component({
  templateUrl: './td-general-delete-dialog.component.html',
})
export class TdGeneralDeleteDialogComponent {
  tdGeneral?: ITdGeneral;

  constructor(protected tdGeneralService: TdGeneralService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tdGeneralService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tdGeneralListModification');
      this.activeModal.close();
    });
  }
}
