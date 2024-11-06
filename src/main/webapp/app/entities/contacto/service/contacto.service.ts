import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContacto, NewContacto } from '../contacto.model';

export type PartialUpdateContacto = Partial<IContacto> & Pick<IContacto, 'id'>;

export type EntityResponseType = HttpResponse<IContacto>;
export type EntityArrayResponseType = HttpResponse<IContacto[]>;

@Injectable({ providedIn: 'root' })
export class ContactoService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contactos');

  create(contacto: NewContacto): Observable<EntityResponseType> {
    return this.http.post<IContacto>(this.resourceUrl, contacto, { observe: 'response' });
  }

  update(contacto: IContacto): Observable<EntityResponseType> {
    return this.http.put<IContacto>(`${this.resourceUrl}/${this.getContactoIdentifier(contacto)}`, contacto, { observe: 'response' });
  }

  partialUpdate(contacto: PartialUpdateContacto): Observable<EntityResponseType> {
    return this.http.patch<IContacto>(`${this.resourceUrl}/${this.getContactoIdentifier(contacto)}`, contacto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContacto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContacto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getContactoIdentifier(contacto: Pick<IContacto, 'id'>): number {
    return contacto.id;
  }

  compareContacto(o1: Pick<IContacto, 'id'> | null, o2: Pick<IContacto, 'id'> | null): boolean {
    return o1 && o2 ? this.getContactoIdentifier(o1) === this.getContactoIdentifier(o2) : o1 === o2;
  }

  addContactoToCollectionIfMissing<Type extends Pick<IContacto, 'id'>>(
    contactoCollection: Type[],
    ...contactosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const contactos: Type[] = contactosToCheck.filter(isPresent);
    if (contactos.length > 0) {
      const contactoCollectionIdentifiers = contactoCollection.map(contactoItem => this.getContactoIdentifier(contactoItem));
      const contactosToAdd = contactos.filter(contactoItem => {
        const contactoIdentifier = this.getContactoIdentifier(contactoItem);
        if (contactoCollectionIdentifiers.includes(contactoIdentifier)) {
          return false;
        }
        contactoCollectionIdentifiers.push(contactoIdentifier);
        return true;
      });
      return [...contactosToAdd, ...contactoCollection];
    }
    return contactoCollection;
  }
}
