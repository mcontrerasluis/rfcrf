import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITcTipoSol } from 'app/shared/model/tc-tipo-sol.model';

type EntityResponseType = HttpResponse<ITcTipoSol>;
type EntityArrayResponseType = HttpResponse<ITcTipoSol[]>;

@Injectable({ providedIn: 'root' })
export class TcTipoSolService {
  public resourceUrl = SERVER_API_URL + 'api/tc-tipo-sols';

  constructor(protected http: HttpClient) {}

  create(tcTipoSol: ITcTipoSol): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tcTipoSol);
    return this.http
      .post<ITcTipoSol>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tcTipoSol: ITcTipoSol): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tcTipoSol);
    return this.http
      .put<ITcTipoSol>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITcTipoSol>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITcTipoSol[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tcTipoSol: ITcTipoSol): ITcTipoSol {
    const copy: ITcTipoSol = Object.assign({}, tcTipoSol, {
      fechaInicio: tcTipoSol.fechaInicio && tcTipoSol.fechaInicio.isValid() ? tcTipoSol.fechaInicio.toJSON() : undefined,
      fechaFin: tcTipoSol.fechaFin && tcTipoSol.fechaFin.isValid() ? tcTipoSol.fechaFin.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaInicio = res.body.fechaInicio ? moment(res.body.fechaInicio) : undefined;
      res.body.fechaFin = res.body.fechaFin ? moment(res.body.fechaFin) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tcTipoSol: ITcTipoSol) => {
        tcTipoSol.fechaInicio = tcTipoSol.fechaInicio ? moment(tcTipoSol.fechaInicio) : undefined;
        tcTipoSol.fechaFin = tcTipoSol.fechaFin ? moment(tcTipoSol.fechaFin) : undefined;
      });
    }
    return res;
  }
}
