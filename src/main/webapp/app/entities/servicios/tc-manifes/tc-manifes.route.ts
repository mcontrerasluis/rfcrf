import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITcManifes, TcManifes } from 'app/shared/model/servicios/tc-manifes.model';
import { TcManifesService } from './tc-manifes.service';
import { TcManifesComponent } from './tc-manifes.component';
import { TcManifesDetailComponent } from './tc-manifes-detail.component';
import { TcManifesUpdateComponent } from './tc-manifes-update.component';

@Injectable({ providedIn: 'root' })
export class TcManifesResolve implements Resolve<ITcManifes> {
  constructor(private service: TcManifesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITcManifes> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tcManifes: HttpResponse<TcManifes>) => {
          if (tcManifes.body) {
            return of(tcManifes.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TcManifes());
  }
}

export const tcManifesRoute: Routes = [
  {
    path: '',
    component: TcManifesComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'fronterizaApp.serviciosTcManifes.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TcManifesDetailComponent,
    resolve: {
      tcManifes: TcManifesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fronterizaApp.serviciosTcManifes.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TcManifesUpdateComponent,
    resolve: {
      tcManifes: TcManifesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fronterizaApp.serviciosTcManifes.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TcManifesUpdateComponent,
    resolve: {
      tcManifes: TcManifesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fronterizaApp.serviciosTcManifes.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
