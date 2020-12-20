import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITdRegFront } from 'app/shared/model/td-reg-front.model';

type EntityResponseType = HttpResponse<ITdRegFront>;
type EntityArrayResponseType = HttpResponse<ITdRegFront[]>;

@Injectable({ providedIn: 'root' })
export class TdRegFrontService {
  public resourceUrl = SERVER_API_URL + 'api/td-reg-fronts';

  constructor(protected http: HttpClient) {}

  create(tdRegFront: ITdRegFront): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tdRegFront);
    return this.http
      .post<ITdRegFront>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tdRegFront: ITdRegFront): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tdRegFront);
    return this.http
      .put<ITdRegFront>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITdRegFront>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITdRegFront[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tdRegFront: ITdRegFront): ITdRegFront {
    const copy: ITdRegFront = Object.assign({}, tdRegFront, {
      fecha: tdRegFront.fecha && tdRegFront.fecha.isValid() ? tdRegFront.fecha.toJSON() : undefined,
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
      res.body.forEach((tdRegFront: ITdRegFront) => {
        tdRegFront.fecha = tdRegFront.fecha ? moment(tdRegFront.fecha) : undefined;
      });
    }
    return res;
  }
}
