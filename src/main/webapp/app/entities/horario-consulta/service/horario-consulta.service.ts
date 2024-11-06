import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHorarioConsulta, NewHorarioConsulta } from '../horario-consulta.model';

export type PartialUpdateHorarioConsulta = Partial<IHorarioConsulta> & Pick<IHorarioConsulta, 'id'>;

type RestOf<T extends IHorarioConsulta | NewHorarioConsulta> = Omit<T, 'fechaHorario' | 'horaInicio'> & {
  fechaHorario?: string | null;
  horaInicio?: string | null;
};

export type RestHorarioConsulta = RestOf<IHorarioConsulta>;

export type NewRestHorarioConsulta = RestOf<NewHorarioConsulta>;

export type PartialUpdateRestHorarioConsulta = RestOf<PartialUpdateHorarioConsulta>;

export type EntityResponseType = HttpResponse<IHorarioConsulta>;
export type EntityArrayResponseType = HttpResponse<IHorarioConsulta[]>;

@Injectable({ providedIn: 'root' })
export class HorarioConsultaService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/horario-consultas');

  create(horarioConsulta: NewHorarioConsulta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(horarioConsulta);
    return this.http
      .post<RestHorarioConsulta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(horarioConsulta: IHorarioConsulta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(horarioConsulta);
    return this.http
      .put<RestHorarioConsulta>(`${this.resourceUrl}/${this.getHorarioConsultaIdentifier(horarioConsulta)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(horarioConsulta: PartialUpdateHorarioConsulta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(horarioConsulta);
    return this.http
      .patch<RestHorarioConsulta>(`${this.resourceUrl}/${this.getHorarioConsultaIdentifier(horarioConsulta)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestHorarioConsulta>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestHorarioConsulta[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getHorarioConsultaIdentifier(horarioConsulta: Pick<IHorarioConsulta, 'id'>): number {
    return horarioConsulta.id;
  }

  compareHorarioConsulta(o1: Pick<IHorarioConsulta, 'id'> | null, o2: Pick<IHorarioConsulta, 'id'> | null): boolean {
    return o1 && o2 ? this.getHorarioConsultaIdentifier(o1) === this.getHorarioConsultaIdentifier(o2) : o1 === o2;
  }

  addHorarioConsultaToCollectionIfMissing<Type extends Pick<IHorarioConsulta, 'id'>>(
    horarioConsultaCollection: Type[],
    ...horarioConsultasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const horarioConsultas: Type[] = horarioConsultasToCheck.filter(isPresent);
    if (horarioConsultas.length > 0) {
      const horarioConsultaCollectionIdentifiers = horarioConsultaCollection.map(horarioConsultaItem =>
        this.getHorarioConsultaIdentifier(horarioConsultaItem),
      );
      const horarioConsultasToAdd = horarioConsultas.filter(horarioConsultaItem => {
        const horarioConsultaIdentifier = this.getHorarioConsultaIdentifier(horarioConsultaItem);
        if (horarioConsultaCollectionIdentifiers.includes(horarioConsultaIdentifier)) {
          return false;
        }
        horarioConsultaCollectionIdentifiers.push(horarioConsultaIdentifier);
        return true;
      });
      return [...horarioConsultasToAdd, ...horarioConsultaCollection];
    }
    return horarioConsultaCollection;
  }

  protected convertDateFromClient<T extends IHorarioConsulta | NewHorarioConsulta | PartialUpdateHorarioConsulta>(
    horarioConsulta: T,
  ): RestOf<T> {
    return {
      ...horarioConsulta,
      fechaHorario: horarioConsulta.fechaHorario?.format(DATE_FORMAT) ?? null,
      horaInicio: horarioConsulta.horaInicio?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restHorarioConsulta: RestHorarioConsulta): IHorarioConsulta {
    return {
      ...restHorarioConsulta,
      fechaHorario: restHorarioConsulta.fechaHorario ? dayjs(restHorarioConsulta.fechaHorario) : undefined,
      horaInicio: restHorarioConsulta.horaInicio ? dayjs(restHorarioConsulta.horaInicio) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestHorarioConsulta>): HttpResponse<IHorarioConsulta> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestHorarioConsulta[]>): HttpResponse<IHorarioConsulta[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
