import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITcEjerc, TcEjerc } from 'app/shared/model/servicios/tc-ejerc.model';
import { TcEjercService } from './tc-ejerc.service';
import { TcEjercComponent } from './tc-ejerc.component';
import { TcEjercDetailComponent } from './tc-ejerc-detail.component';
import { TcEjercUpdateComponent } from './tc-ejerc-update.component';

@Injectable({ providedIn: 'root' })
export class TcEjercResolve implements Resolve<ITcEjerc> {
  constructor(private service: TcEjercService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITcEjerc> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tcEjerc: HttpResponse<TcEjerc>) => {
          if (tcEjerc.body) {
            return of(tcEjerc.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TcEjerc());
  }
}

export const tcEjercRoute: Routes = [
  {
    path: '',
    component: TcEjercComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'fronterizaApp.serviciosTcEjerc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TcEjercDetailComponent,
    resolve: {
      tcEjerc: TcEjercResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fronterizaApp.serviciosTcEjerc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TcEjercUpdateComponent,
    resolve: {
      tcEjerc: TcEjercResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fronterizaApp.serviciosTcEjerc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TcEjercUpdateComponent,
    resolve: {
      tcEjerc: TcEjercResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fronterizaApp.serviciosTcEjerc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
