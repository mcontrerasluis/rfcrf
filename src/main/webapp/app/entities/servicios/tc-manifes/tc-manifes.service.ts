import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITcManifes } from 'app/shared/model/servicios/tc-manifes.model';

type EntityResponseType = HttpResponse<ITcManifes>;
type EntityArrayResponseType = HttpResponse<ITcManifes[]>;

@Injectable({ providedIn: 'root' })
export class TcManifesService {
  public resourceUrl = SERVER_API_URL + 'api/tc-manifes';

  constructor(protected http: HttpClient) {}

  create(tcManifes: ITcManifes): Observable<EntityResponseType> {
    return this.http.post<ITcManifes>(this.resourceUrl, tcManifes, { observe: 'response' });
  }

  update(tcManifes: ITcManifes): Observable<EntityResponseType> {
    return this.http.put<ITcManifes>(this.resourceUrl, tcManifes, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITcManifes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITcManifes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
