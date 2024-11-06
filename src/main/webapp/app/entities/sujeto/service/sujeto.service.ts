import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISujeto, NewSujeto } from '../sujeto.model';

export type PartialUpdateSujeto = Partial<ISujeto> & Pick<ISujeto, 'id'>;

type RestOf<T extends ISujeto | NewSujeto> = Omit<T, 'fechaNacimiento'> & {
  fechaNacimiento?: string | null;
};

export type RestSujeto = RestOf<ISujeto>;

export type NewRestSujeto = RestOf<NewSujeto>;

export type PartialUpdateRestSujeto = RestOf<PartialUpdateSujeto>;

export type EntityResponseType = HttpResponse<ISujeto>;
export type EntityArrayResponseType = HttpResponse<ISujeto[]>;

@Injectable({ providedIn: 'root' })
export class SujetoService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sujetos');

  create(sujeto: NewSujeto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sujeto);
    return this.http
      .post<RestSujeto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(sujeto: ISujeto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sujeto);
    return this.http
      .put<RestSujeto>(`${this.resourceUrl}/${this.getSujetoIdentifier(sujeto)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(sujeto: PartialUpdateSujeto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sujeto);
    return this.http
      .patch<RestSujeto>(`${this.resourceUrl}/${this.getSujetoIdentifier(sujeto)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSujeto>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSujeto[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSujetoIdentifier(sujeto: Pick<ISujeto, 'id'>): number {
    return sujeto.id;
  }

  compareSujeto(o1: Pick<ISujeto, 'id'> | null, o2: Pick<ISujeto, 'id'> | null): boolean {
    return o1 && o2 ? this.getSujetoIdentifier(o1) === this.getSujetoIdentifier(o2) : o1 === o2;
  }

  addSujetoToCollectionIfMissing<Type extends Pick<ISujeto, 'id'>>(
    sujetoCollection: Type[],
    ...sujetosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const sujetos: Type[] = sujetosToCheck.filter(isPresent);
    if (sujetos.length > 0) {
      const sujetoCollectionIdentifiers = sujetoCollection.map(sujetoItem => this.getSujetoIdentifier(sujetoItem));
      const sujetosToAdd = sujetos.filter(sujetoItem => {
        const sujetoIdentifier = this.getSujetoIdentifier(sujetoItem);
        if (sujetoCollectionIdentifiers.includes(sujetoIdentifier)) {
          return false;
        }
        sujetoCollectionIdentifiers.push(sujetoIdentifier);
        return true;
      });
      return [...sujetosToAdd, ...sujetoCollection];
    }
    return sujetoCollection;
  }

  protected convertDateFromClient<T extends ISujeto | NewSujeto | PartialUpdateSujeto>(sujeto: T): RestOf<T> {
    return {
      ...sujeto,
      fechaNacimiento: sujeto.fechaNacimiento?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restSujeto: RestSujeto): ISujeto {
    return {
      ...restSujeto,
      fechaNacimiento: restSujeto.fechaNacimiento ? dayjs(restSujeto.fechaNacimiento) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSujeto>): HttpResponse<ISujeto> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSujeto[]>): HttpResponse<ISujeto[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
