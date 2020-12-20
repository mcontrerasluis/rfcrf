import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RfcrfSharedModule } from 'app/shared/shared.module';
import { TcTipoSolComponent } from './tc-tipo-sol.component';
import { TcTipoSolDetailComponent } from './tc-tipo-sol-detail.component';
import { TcTipoSolUpdateComponent } from './tc-tipo-sol-update.component';
import { TcTipoSolDeleteDialogComponent } from './tc-tipo-sol-delete-dialog.component';
import { tcTipoSolRoute } from './tc-tipo-sol.route';

@NgModule({
  imports: [RfcrfSharedModule, RouterModule.forChild(tcTipoSolRoute)],
  declarations: [TcTipoSolComponent, TcTipoSolDetailComponent, TcTipoSolUpdateComponent, TcTipoSolDeleteDialogComponent],
  entryComponents: [TcTipoSolDeleteDialogComponent],
})
export class ServiciosTcTipoSolModule {}
