import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RfcrfSharedModule } from 'app/shared/shared.module';
import { TcValidaComponent } from './tc-valida.component';
import { TcValidaDetailComponent } from './tc-valida-detail.component';
import { TcValidaUpdateComponent } from './tc-valida-update.component';
import { TcValidaDeleteDialogComponent } from './tc-valida-delete-dialog.component';
import { tcValidaRoute } from './tc-valida.route';

@NgModule({
  imports: [RfcrfSharedModule, RouterModule.forChild(tcValidaRoute)],
  declarations: [TcValidaComponent, TcValidaDetailComponent, TcValidaUpdateComponent, TcValidaDeleteDialogComponent],
  entryComponents: [TcValidaDeleteDialogComponent],
})
export class ServiciosTcValidaModule {}
