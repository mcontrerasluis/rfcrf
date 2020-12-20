import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITcTipoImp, TcTipoImp } from 'app/shared/model/servicios/tc-tipo-imp.model';
import { TcTipoImpService } from './tc-tipo-imp.service';
import { TcTipoImpComponent } from './tc-tipo-imp.component';
import { TcTipoImpDetailComponent } from './tc-tipo-imp-detail.component';
import { TcTipoImpUpdateComponent } from './tc-tipo-imp-update.component';

@Injectable({ providedIn: 'root' })
export class TcTipoImpResolve implements Resolve<ITcTipoImp> {
  constructor(private service: TcTipoImpService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITcTipoImp> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tcTipoImp: HttpResponse<TcTipoImp>) => {
          if (tcTipoImp.body) {
            return of(tcTipoImp.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TcTipoImp());
  }
}

export const tcTipoImpRoute: Routes = [
  {
    path: '',
    component: TcTipoImpComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'fronterizaApp.serviciosTcTipoImp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TcTipoImpDetailComponent,
    resolve: {
      tcTipoImp: TcTipoImpResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fronterizaApp.serviciosTcTipoImp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TcTipoImpUpdateComponent,
    resolve: {
      tcTipoImp: TcTipoImpResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fronterizaApp.serviciosTcTipoImp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TcTipoImpUpdateComponent,
    resolve: {
      tcTipoImp: TcTipoImpResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fronterizaApp.serviciosTcTipoImp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
