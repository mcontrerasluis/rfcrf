import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITcEjerc } from 'app/shared/model/tc-ejerc.model';

type EntityResponseType = HttpResponse<ITcEjerc>;
type EntityArrayResponseType = HttpResponse<ITcEjerc[]>;

@Injectable({ providedIn: 'root' })
export class TcEjercService {
  public resourceUrl = SERVER_API_URL + 'api/tc-ejercs';

  constructor(protected http: HttpClient) {}

  create(tcEjerc: ITcEjerc): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tcEjerc);
    return this.http
      .post<ITcEjerc>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tcEjerc: ITcEjerc): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tcEjerc);
    return this.http
      .put<ITcEjerc>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITcEjerc>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITcEjerc[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tcEjerc: ITcEjerc): ITcEjerc {
    const copy: ITcEjerc = Object.assign({}, tcEjerc, {
      fechaInicio: tcEjerc.fechaInicio && tcEjerc.fechaInicio.isValid() ? tcEjerc.fechaInicio.toJSON() : undefined,
      fechaFin: tcEjerc.fechaFin && tcEjerc.fechaFin.isValid() ? tcEjerc.fechaFin.toJSON() : undefined,
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
      res.body.forEach((tcEjerc: ITcEjerc) => {
        tcEjerc.fechaInicio = tcEjerc.fechaInicio ? moment(tcEjerc.fechaInicio) : undefined;
        tcEjerc.fechaFin = tcEjerc.fechaFin ? moment(tcEjerc.fechaFin) : undefined;
      });
    }
    return res;
  }
}
