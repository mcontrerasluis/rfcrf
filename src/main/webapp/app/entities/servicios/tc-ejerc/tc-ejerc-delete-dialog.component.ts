import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITcEjerc } from 'app/shared/model/servicios/tc-ejerc.model';
import { TcEjercService } from './tc-ejerc.service';

@Component({
  templateUrl: './tc-ejerc-delete-dialog.component.html',
})
export class TcEjercDeleteDialogComponent {
  tcEjerc?: ITcEjerc;

  constructor(protected tcEjercService: TcEjercService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tcEjercService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tcEjercListModification');
      this.activeModal.close();
    });
  }
}
