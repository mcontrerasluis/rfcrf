import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITcManifes } from 'app/shared/model/tc-manifes.model';

type EntityResponseType = HttpResponse<ITcManifes>;
type EntityArrayResponseType = HttpResponse<ITcManifes[]>;

@Injectable({ providedIn: 'root' })
export class TcManifesService {
  public resourceUrl = SERVER_API_URL + 'api/tc-manifes';

  constructor(protected http: HttpClient) {}

  create(tcManifes: ITcManifes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tcManifes);
    return this.http
      .post<ITcManifes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tcManifes: ITcManifes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tcManifes);
    return this.http
      .put<ITcManifes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITcManifes>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITcManifes[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tcManifes: ITcManifes): ITcManifes {
    const copy: ITcManifes = Object.assign({}, tcManifes, {
      fecha: tcManifes.fecha && tcManifes.fecha.isValid() ? tcManifes.fecha.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha ? moment(res.body.fecha) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tcManifes: ITcManifes) => {
        tcManifes.fecha = tcManifes.fecha ? moment(tcManifes.fecha) : undefined;
      });
    }
    return res;
  }
}
