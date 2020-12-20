import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITcManifes } from 'app/shared/model/servicios/tc-manifes.model';
import { TcManifesService } from './tc-manifes.service';

@Component({
  templateUrl: './tc-manifes-delete-dialog.component.html',
})
export class TcManifesDeleteDialogComponent {
  tcManifes?: ITcManifes;

  constructor(protected tcManifesService: TcManifesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tcManifesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tcManifesListModification');
      this.activeModal.close();
    });
  }
}
