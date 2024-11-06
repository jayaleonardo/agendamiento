import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITipoTerapia, NewTipoTerapia } from '../tipo-terapia.model';

export type PartialUpdateTipoTerapia = Partial<ITipoTerapia> & Pick<ITipoTerapia, 'id'>;

export type EntityResponseType = HttpResponse<ITipoTerapia>;
export type EntityArrayResponseType = HttpResponse<ITipoTerapia[]>;

@Injectable({ providedIn: 'root' })
export class TipoTerapiaService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tipo-terapias');

  create(tipoTerapia: NewTipoTerapia): Observable<EntityResponseType> {
    return this.http.post<ITipoTerapia>(this.resourceUrl, tipoTerapia, { observe: 'response' });
  }

  update(tipoTerapia: ITipoTerapia): Observable<EntityResponseType> {
    return this.http.put<ITipoTerapia>(`${this.resourceUrl}/${this.getTipoTerapiaIdentifier(tipoTerapia)}`, tipoTerapia, {
      observe: 'response',
    });
  }

  partialUpdate(tipoTerapia: PartialUpdateTipoTerapia): Observable<EntityResponseType> {
    return this.http.patch<ITipoTerapia>(`${this.resourceUrl}/${this.getTipoTerapiaIdentifier(tipoTerapia)}`, tipoTerapia, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoTerapia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoTerapia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTipoTerapiaIdentifier(tipoTerapia: Pick<ITipoTerapia, 'id'>): number {
    return tipoTerapia.id;
  }

  compareTipoTerapia(o1: Pick<ITipoTerapia, 'id'> | null, o2: Pick<ITipoTerapia, 'id'> | null): boolean {
    return o1 && o2 ? this.getTipoTerapiaIdentifier(o1) === this.getTipoTerapiaIdentifier(o2) : o1 === o2;
  }

  addTipoTerapiaToCollectionIfMissing<Type extends Pick<ITipoTerapia, 'id'>>(
    tipoTerapiaCollection: Type[],
    ...tipoTerapiasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tipoTerapias: Type[] = tipoTerapiasToCheck.filter(isPresent);
    if (tipoTerapias.length > 0) {
      const tipoTerapiaCollectionIdentifiers = tipoTerapiaCollection.map(tipoTerapiaItem => this.getTipoTerapiaIdentifier(tipoTerapiaItem));
      const tipoTerapiasToAdd = tipoTerapias.filter(tipoTerapiaItem => {
        const tipoTerapiaIdentifier = this.getTipoTerapiaIdentifier(tipoTerapiaItem);
        if (tipoTerapiaCollectionIdentifiers.includes(tipoTerapiaIdentifier)) {
          return false;
        }
        tipoTerapiaCollectionIdentifiers.push(tipoTerapiaIdentifier);
        return true;
      });
      return [...tipoTerapiasToAdd, ...tipoTerapiaCollection];
    }
    return tipoTerapiaCollection;
  }
}
