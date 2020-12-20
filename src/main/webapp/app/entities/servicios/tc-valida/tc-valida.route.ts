import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITcValida, TcValida } from 'app/shared/model/servicios/tc-valida.model';
import { TcValidaService } from './tc-valida.service';
import { TcValidaComponent } from './tc-valida.component';
import { TcValidaDetailComponent } from './tc-valida-detail.component';
import { TcValidaUpdateComponent } from './tc-valida-update.component';

@Injectable({ providedIn: 'root' })
export class TcValidaResolve implements Resolve<ITcValida> {
  constructor(private service: TcValidaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITcValida> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tcValida: HttpResponse<TcValida>) => {
          if (tcValida.body) {
            return of(tcValida.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TcValida());
  }
}

export const tcValidaRoute: Routes = [
  {
    path: '',
    component: TcValidaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'fronterizaApp.serviciosTcValida.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TcValidaDetailComponent,
    resolve: {
      tcValida: TcValidaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fronterizaApp.serviciosTcValida.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TcValidaUpdateComponent,
    resolve: {
      tcValida: TcValidaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fronterizaApp.serviciosTcValida.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TcValidaUpdateComponent,
    resolve: {
      tcValida: TcValidaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fronterizaApp.serviciosTcValida.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
