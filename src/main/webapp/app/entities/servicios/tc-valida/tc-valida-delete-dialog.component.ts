import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITcValida } from 'app/shared/model/servicios/tc-valida.model';
import { TcValidaService } from './tc-valida.service';

@Component({
  templateUrl: './tc-valida-delete-dialog.component.html',
})
export class TcValidaDeleteDialogComponent {
  tcValida?: ITcValida;

  constructor(protected tcValidaService: TcValidaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tcValidaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tcValidaListModification');
      this.activeModal.close();
    });
  }
}
