import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITdRegFront } from 'app/shared/model/servicios/td-reg-front.model';

type EntityResponseType = HttpResponse<ITdRegFront>;
type EntityArrayResponseType = HttpResponse<ITdRegFront[]>;

@Injectable({ providedIn: 'root' })
export class TdRegFrontService {
  public resourceUrl = SERVER_API_URL + 'api/td-reg-fronts';

  constructor(protected http: HttpClient) {}

  create(tdRegFront: ITdRegFront): Observable<EntityResponseType> {
    return this.http.post<ITdRegFront>(this.resourceUrl, tdRegFront, { observe: 'response' });
  }

  update(tdRegFront: ITdRegFront): Observable<EntityResponseType> {
    return this.http.put<ITdRegFront>(this.resourceUrl, tdRegFront, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITdRegFront>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITdRegFront[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  acuse(): any {
    return SERVER_API_URL + 'api/getpdf';
  }
}
