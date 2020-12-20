import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RfcrfSharedModule } from 'app/shared/shared.module';
import { TdGeneralComponent } from './td-general.component';
import { TdGeneralDetailComponent } from './td-general-detail.component';
import { TdGeneralUpdateComponent } from './td-general-update.component';
import { TdGeneralDeleteDialogComponent } from './td-general-delete-dialog.component';
import { tdGeneralRoute } from './td-general.route';

@NgModule({
  imports: [RfcrfSharedModule, RouterModule.forChild(tdGeneralRoute)],
  declarations: [TdGeneralComponent, TdGeneralDetailComponent, TdGeneralUpdateComponent, TdGeneralDeleteDialogComponent],
  entryComponents: [TdGeneralDeleteDialogComponent],
})
export class RfcrfTdGeneralModule {}
