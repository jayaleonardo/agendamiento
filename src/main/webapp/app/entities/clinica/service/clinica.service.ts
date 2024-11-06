import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IClinica, NewClinica } from '../clinica.model';

export type PartialUpdateClinica = Partial<IClinica> & Pick<IClinica, 'id'>;

export type EntityResponseType = HttpResponse<IClinica>;
export type EntityArrayResponseType = HttpResponse<IClinica[]>;

@Injectable({ providedIn: 'root' })
export class ClinicaService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/clinicas');

  create(clinica: NewClinica): Observable<EntityResponseType> {
    return this.http.post<IClinica>(this.resourceUrl, clinica, { observe: 'response' });
  }

  update(clinica: IClinica): Observable<EntityResponseType> {
    return this.http.put<IClinica>(`${this.resourceUrl}/${this.getClinicaIdentifier(clinica)}`, clinica, { observe: 'response' });
  }

  partialUpdate(clinica: PartialUpdateClinica): Observable<EntityResponseType> {
    return this.http.patch<IClinica>(`${this.resourceUrl}/${this.getClinicaIdentifier(clinica)}`, clinica, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IClinica>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClinica[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getClinicaIdentifier(clinica: Pick<IClinica, 'id'>): number {
    return clinica.id;
  }

  compareClinica(o1: Pick<IClinica, 'id'> | null, o2: Pick<IClinica, 'id'> | null): boolean {
    return o1 && o2 ? this.getClinicaIdentifier(o1) === this.getClinicaIdentifier(o2) : o1 === o2;
  }

  addClinicaToCollectionIfMissing<Type extends Pick<IClinica, 'id'>>(
    clinicaCollection: Type[],
    ...clinicasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const clinicas: Type[] = clinicasToCheck.filter(isPresent);
    if (clinicas.length > 0) {
      const clinicaCollectionIdentifiers = clinicaCollection.map(clinicaItem => this.getClinicaIdentifier(clinicaItem));
      const clinicasToAdd = clinicas.filter(clinicaItem => {
        const clinicaIdentifier = this.getClinicaIdentifier(clinicaItem);
        if (clinicaCollectionIdentifiers.includes(clinicaIdentifier)) {
          return false;
        }
        clinicaCollectionIdentifiers.push(clinicaIdentifier);
        return true;
      });
      return [...clinicasToAdd, ...clinicaCollection];
    }
    return clinicaCollection;
  }
}
