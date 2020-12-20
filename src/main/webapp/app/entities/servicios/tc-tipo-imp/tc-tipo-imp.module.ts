import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RfcrfSharedModule } from 'app/shared/shared.module';
import { TcTipoImpComponent } from './tc-tipo-imp.component';
import { TcTipoImpDetailComponent } from './tc-tipo-imp-detail.component';
import { TcTipoImpUpdateComponent } from './tc-tipo-imp-update.component';
import { TcTipoImpDeleteDialogComponent } from './tc-tipo-imp-delete-dialog.component';
import { tcTipoImpRoute } from './tc-tipo-imp.route';

@NgModule({
  imports: [RfcrfSharedModule, RouterModule.forChild(tcTipoImpRoute)],
  declarations: [TcTipoImpComponent, TcTipoImpDetailComponent, TcTipoImpUpdateComponent, TcTipoImpDeleteDialogComponent],
  entryComponents: [TcTipoImpDeleteDialogComponent],
})
export class ServiciosTcTipoImpModule {}
