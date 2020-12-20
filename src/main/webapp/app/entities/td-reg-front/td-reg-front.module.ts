import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RfcrfSharedModule } from 'app/shared/shared.module';
import { TdRegFrontComponent } from './td-reg-front.component';
import { TdRegFrontDetailComponent } from './td-reg-front-detail.component';
import { TdRegFrontUpdateComponent } from './td-reg-front-update.component';
import { TdRegFrontDeleteDialogComponent } from './td-reg-front-delete-dialog.component';
import { tdRegFrontRoute } from './td-reg-front.route';

@NgModule({
  imports: [RfcrfSharedModule, RouterModule.forChild(tdRegFrontRoute)],
  declarations: [TdRegFrontComponent, TdRegFrontDetailComponent, TdRegFrontUpdateComponent, TdRegFrontDeleteDialogComponent],
  entryComponents: [TdRegFrontDeleteDialogComponent],
})
export class RfcrfTdRegFrontModule {}
