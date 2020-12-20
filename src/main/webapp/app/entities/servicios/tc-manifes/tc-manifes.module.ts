import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RfcrfSharedModule } from 'app/shared/shared.module';
import { TcManifesComponent } from './tc-manifes.component';
import { TcManifesDetailComponent } from './tc-manifes-detail.component';
import { TcManifesUpdateComponent } from './tc-manifes-update.component';
import { TcManifesDeleteDialogComponent } from './tc-manifes-delete-dialog.component';
import { tcManifesRoute } from './tc-manifes.route';

@NgModule({
  imports: [RfcrfSharedModule, RouterModule.forChild(tcManifesRoute)],
  declarations: [TcManifesComponent, TcManifesDetailComponent, TcManifesUpdateComponent, TcManifesDeleteDialogComponent],
  entryComponents: [TcManifesDeleteDialogComponent],
})
export class ServiciosTcManifesModule {}
