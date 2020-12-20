import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITdGeneral, TdGeneral } from 'app/shared/model/servicios/td-general.model';
import { TdGeneralService } from './td-general.service';
import { TdGeneralComponent } from './td-general.component';
import { TdGeneralDetailComponent } from './td-general-detail.component';
import { TdGeneralUpdateComponent } from './td-general-update.component';

@Injectable({ providedIn: 'root' })
export class TdGeneralResolve implements Resolve<ITdGeneral> {
  constructor(private service: TdGeneralService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITdGeneral> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tdGeneral: HttpResponse<TdGeneral>) => {
          if (tdGeneral.body) {
            return of(tdGeneral.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TdGeneral());
  }
}

export const tdGeneralRoute: Routes = [
  {
    path: '',
    component: TdGeneralComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'fronterizaApp.serviciosTdGeneral.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TdGeneralDetailComponent,
    resolve: {
      tdGeneral: TdGeneralResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fronterizaApp.serviciosTdGeneral.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TdGeneralUpdateComponent,
    resolve: {
      tdGeneral: TdGeneralResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fronterizaApp.serviciosTdGeneral.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TdGeneralUpdateComponent,
    resolve: {
      tdGeneral: TdGeneralResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fronterizaApp.serviciosTdGeneral.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
