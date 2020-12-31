import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RfcrfSharedModule } from 'app/shared/shared.module';
import { TdRegFrontComponent } from './td-reg-front.component';
import { TdRegFrontDetailComponent } from './td-reg-front-detail.component';
import { TdRegFrontUpdateComponent } from './td-reg-front-update.component';
import { TdRegFrontDeleteDialogComponent } from './td-reg-front-delete-dialog.component';
import {ControlManifestacionComponent} from '../../../control-manifestacion/control-manifestacion.component'
import { tdRegFrontRoute } from './td-reg-front.route';

import { TableModule } from 'primeng/table';
import { CheckboxModule } from 'primeng/checkbox';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { FileUploadModule } from 'primeng/fileupload';
import { DialogModule } from 'primeng/dialog';
import { StepsModule } from 'primeng/steps';
import { CardModule } from 'primeng/card';
import { DropdownModule } from 'primeng/dropdown';
import { PaginatorModule } from 'primeng/paginator';
import { ToggleButtonModule } from 'primeng/togglebutton';

import { NgxExtendedPdfViewerModule } from 'ngx-extended-pdf-viewer';

@NgModule({
  imports: [
    RfcrfSharedModule,
    RouterModule.forChild(tdRegFrontRoute),
    TableModule,
    TableModule,
    CheckboxModule,
    ToastModule,
    ToolbarModule,
    ConfirmDialogModule,
    FileUploadModule,
    DialogModule,
    StepsModule,
    CardModule,
    DropdownModule,
    PaginatorModule,
    ToggleButtonModule,
    NgxExtendedPdfViewerModule,
  ],
  declarations: [TdRegFrontComponent, TdRegFrontDetailComponent, TdRegFrontUpdateComponent, TdRegFrontDeleteDialogComponent, ControlManifestacionComponent],
  entryComponents: [TdRegFrontDeleteDialogComponent],
})
export class ServiciosTdRegFrontModule {}
