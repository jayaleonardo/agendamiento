import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEspecialista, NewEspecialista } from '../especialista.model';

export type PartialUpdateEspecialista = Partial<IEspecialista> & Pick<IEspecialista, 'id'>;

export type EntityResponseType = HttpResponse<IEspecialista>;
export type EntityArrayResponseType = HttpResponse<IEspecialista[]>;

@Injectable({ providedIn: 'root' })
export class EspecialistaService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/especialistas');

  create(especialista: NewEspecialista): Observable<EntityResponseType> {
    return this.http.post<IEspecialista>(this.resourceUrl, especialista, { observe: 'response' });
  }

  update(especialista: IEspecialista): Observable<EntityResponseType> {
    return this.http.put<IEspecialista>(`${this.resourceUrl}/${this.getEspecialistaIdentifier(especialista)}`, especialista, {
      observe: 'response',
    });
  }

  partialUpdate(especialista: PartialUpdateEspecialista): Observable<EntityResponseType> {
    return this.http.patch<IEspecialista>(`${this.resourceUrl}/${this.getEspecialistaIdentifier(especialista)}`, especialista, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEspecialista>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEspecialista[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEspecialistaIdentifier(especialista: Pick<IEspecialista, 'id'>): number {
    return especialista.id;
  }

  compareEspecialista(o1: Pick<IEspecialista, 'id'> | null, o2: Pick<IEspecialista, 'id'> | null): boolean {
    return o1 && o2 ? this.getEspecialistaIdentifier(o1) === this.getEspecialistaIdentifier(o2) : o1 === o2;
  }

  addEspecialistaToCollectionIfMissing<Type extends Pick<IEspecialista, 'id'>>(
    especialistaCollection: Type[],
    ...especialistasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const especialistas: Type[] = especialistasToCheck.filter(isPresent);
    if (especialistas.length > 0) {
      const especialistaCollectionIdentifiers = especialistaCollection.map(especialistaItem =>
        this.getEspecialistaIdentifier(especialistaItem),
      );
      const especialistasToAdd = especialistas.filter(especialistaItem => {
        const especialistaIdentifier = this.getEspecialistaIdentifier(especialistaItem);
        if (especialistaCollectionIdentifiers.includes(especialistaIdentifier)) {
          return false;
        }
        especialistaCollectionIdentifiers.push(especialistaIdentifier);
        return true;
      });
      return [...especialistasToAdd, ...especialistaCollection];
    }
    return especialistaCollection;
  }
}
