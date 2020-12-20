import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RfcrfSharedModule } from 'app/shared/shared.module';
import { TcEjercComponent } from './tc-ejerc.component';
import { TcEjercDetailComponent } from './tc-ejerc-detail.component';
import { TcEjercUpdateComponent } from './tc-ejerc-update.component';
import { TcEjercDeleteDialogComponent } from './tc-ejerc-delete-dialog.component';
import { tcEjercRoute } from './tc-ejerc.route';

@NgModule({
  imports: [RfcrfSharedModule, RouterModule.forChild(tcEjercRoute)],
  declarations: [TcEjercComponent, TcEjercDetailComponent, TcEjercUpdateComponent, TcEjercDeleteDialogComponent],
  entryComponents: [TcEjercDeleteDialogComponent],
})
export class ServiciosTcEjercModule {}
