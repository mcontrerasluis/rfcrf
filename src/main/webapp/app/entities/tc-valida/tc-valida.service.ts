import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITcValida } from 'app/shared/model/tc-valida.model';

type EntityResponseType = HttpResponse<ITcValida>;
type EntityArrayResponseType = HttpResponse<ITcValida[]>;

@Injectable({ providedIn: 'root' })
export class TcValidaService {
  public resourceUrl = SERVER_API_URL + 'api/tc-validas';

  constructor(protected http: HttpClient) {}

  create(tcValida: ITcValida): Observable<EntityResponseType> {
    return this.http.post<ITcValida>(this.resourceUrl, tcValida, { observe: 'response' });
  }

  update(tcValida: ITcValida): Observable<EntityResponseType> {
    return this.http.put<ITcValida>(this.resourceUrl, tcValida, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITcValida>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITcValida[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
