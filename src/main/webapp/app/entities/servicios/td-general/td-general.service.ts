import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITdGeneral } from 'app/shared/model/servicios/td-general.model';

type EntityResponseType = HttpResponse<ITdGeneral>;
type EntityArrayResponseType = HttpResponse<ITdGeneral[]>;

@Injectable({ providedIn: 'root' })
export class TdGeneralService {
  public resourceUrl = SERVER_API_URL + 'api/td-generals';

  constructor(protected http: HttpClient) {}

  create(tdGeneral: ITdGeneral): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tdGeneral);
    return this.http
      .post<ITdGeneral>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tdGeneral: ITdGeneral): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tdGeneral);
    return this.http
      .put<ITdGeneral>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITdGeneral>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITdGeneral[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tdGeneral: ITdGeneral): ITdGeneral {
    const copy: ITdGeneral = Object.assign({}, tdGeneral, {
      fecha: tdGeneral.fecha && tdGeneral.fecha.isValid() ? tdGeneral.fecha.toJSON() : undefined,
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
      res.body.forEach((tdGeneral: ITdGeneral) => {
        tdGeneral.fecha = tdGeneral.fecha ? moment(tdGeneral.fecha) : undefined;
      });
    }
    return res;
  }
}
