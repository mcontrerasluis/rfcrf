import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITcTipoSol, TcTipoSol } from 'app/shared/model/servicios/tc-tipo-sol.model';
import { TcTipoSolService } from './tc-tipo-sol.service';
import { TcTipoSolComponent } from './tc-tipo-sol.component';
import { TcTipoSolDetailComponent } from './tc-tipo-sol-detail.component';
import { TcTipoSolUpdateComponent } from './tc-tipo-sol-update.component';

@Injectable({ providedIn: 'root' })
export class TcTipoSolResolve implements Resolve<ITcTipoSol> {
  constructor(private service: TcTipoSolService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITcTipoSol> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tcTipoSol: HttpResponse<TcTipoSol>) => {
          if (tcTipoSol.body) {
            return of(tcTipoSol.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TcTipoSol());
  }
}

export const tcTipoSolRoute: Routes = [
  {
    path: '',
    component: TcTipoSolComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'fronterizaApp.serviciosTcTipoSol.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TcTipoSolDetailComponent,
    resolve: {
      tcTipoSol: TcTipoSolResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fronterizaApp.serviciosTcTipoSol.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TcTipoSolUpdateComponent,
    resolve: {
      tcTipoSol: TcTipoSolResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fronterizaApp.serviciosTcTipoSol.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TcTipoSolUpdateComponent,
    resolve: {
      tcTipoSol: TcTipoSolResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fronterizaApp.serviciosTcTipoSol.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
