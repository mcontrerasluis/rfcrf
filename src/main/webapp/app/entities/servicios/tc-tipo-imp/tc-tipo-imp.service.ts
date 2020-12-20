import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITcTipoImp } from 'app/shared/model/servicios/tc-tipo-imp.model';

type EntityResponseType = HttpResponse<ITcTipoImp>;
type EntityArrayResponseType = HttpResponse<ITcTipoImp[]>;

@Injectable({ providedIn: 'root' })
export class TcTipoImpService {
  public resourceUrl = SERVER_API_URL + 'api/tc-tipo-imps';

  constructor(protected http: HttpClient) {}

  create(tcTipoImp: ITcTipoImp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tcTipoImp);
    return this.http
      .post<ITcTipoImp>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tcTipoImp: ITcTipoImp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tcTipoImp);
    return this.http
      .put<ITcTipoImp>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITcTipoImp>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITcTipoImp[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tcTipoImp: ITcTipoImp): ITcTipoImp {
    const copy: ITcTipoImp = Object.assign({}, tcTipoImp, {
      fechaInicio: tcTipoImp.fechaInicio && tcTipoImp.fechaInicio.isValid() ? tcTipoImp.fechaInicio.toJSON() : undefined,
      fechaFin: tcTipoImp.fechaFin && tcTipoImp.fechaFin.isValid() ? tcTipoImp.fechaFin.toJSON() : undefined,
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
      res.body.forEach((tcTipoImp: ITcTipoImp) => {
        tcTipoImp.fechaInicio = tcTipoImp.fechaInicio ? moment(tcTipoImp.fechaInicio) : undefined;
        tcTipoImp.fechaFin = tcTipoImp.fechaFin ? moment(tcTipoImp.fechaFin) : undefined;
      });
    }
    return res;
  }
}
