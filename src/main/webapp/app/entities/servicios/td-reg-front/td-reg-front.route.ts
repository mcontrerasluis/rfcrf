import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITdRegFront, TdRegFront } from 'app/shared/model/servicios/td-reg-front.model';
import { TdRegFrontService } from './td-reg-front.service';
import { TdRegFrontComponent } from './td-reg-front.component';
import { TdRegFrontDetailComponent } from './td-reg-front-detail.component';
import { TdRegFrontUpdateComponent } from './td-reg-front-update.component';

@Injectable({ providedIn: 'root' })
export class TdRegFrontResolve implements Resolve<ITdRegFront> {
  constructor(private service: TdRegFrontService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITdRegFront> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tdRegFront: HttpResponse<TdRegFront>) => {
          if (tdRegFront.body) {
            return of(tdRegFront.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TdRegFront());
  }
}

export const tdRegFrontRoute: Routes = [
  {
    path: '',
    component: TdRegFrontComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'fronterizaApp.serviciosTdRegFront.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TdRegFrontDetailComponent,
    resolve: {
      tdRegFront: TdRegFrontResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fronterizaApp.serviciosTdRegFront.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TdRegFrontUpdateComponent,
    resolve: {
      tdRegFront: TdRegFrontResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fronterizaApp.serviciosTdRegFront.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TdRegFrontUpdateComponent,
    resolve: {
      tdRegFront: TdRegFrontResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fronterizaApp.serviciosTdRegFront.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
