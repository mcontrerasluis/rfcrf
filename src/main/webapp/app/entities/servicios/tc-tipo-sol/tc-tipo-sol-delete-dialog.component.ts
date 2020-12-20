import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITcTipoSol } from 'app/shared/model/servicios/tc-tipo-sol.model';
import { TcTipoSolService } from './tc-tipo-sol.service';

@Component({
  templateUrl: './tc-tipo-sol-delete-dialog.component.html',
})
export class TcTipoSolDeleteDialogComponent {
  tcTipoSol?: ITcTipoSol;

  constructor(protected tcTipoSolService: TcTipoSolService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tcTipoSolService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tcTipoSolListModification');
      this.activeModal.close();
    });
  }
}
