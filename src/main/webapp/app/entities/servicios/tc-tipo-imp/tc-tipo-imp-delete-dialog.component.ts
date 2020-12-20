import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITcTipoImp } from 'app/shared/model/servicios/tc-tipo-imp.model';
import { TcTipoImpService } from './tc-tipo-imp.service';

@Component({
  templateUrl: './tc-tipo-imp-delete-dialog.component.html',
})
export class TcTipoImpDeleteDialogComponent {
  tcTipoImp?: ITcTipoImp;

  constructor(protected tcTipoImpService: TcTipoImpService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tcTipoImpService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tcTipoImpListModification');
      this.activeModal.close();
    });
  }
}
