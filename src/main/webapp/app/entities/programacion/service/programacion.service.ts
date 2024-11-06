import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProgramacion, NewProgramacion } from '../programacion.model';

export type PartialUpdateProgramacion = Partial<IProgramacion> & Pick<IProgramacion, 'id'>;

type RestOf<T extends IProgramacion | NewProgramacion> = Omit<
  T,
  'fechaDesde' | 'fechaHasta' | 'desdeHoraAlmuerzo' | 'hastaHoraAlmuerzo'
> & {
  fechaDesde?: string | null;
  fechaHasta?: string | null;
  desdeHoraAlmuerzo?: string | null;
  hastaHoraAlmuerzo?: string | null;
};

export type RestProgramacion = RestOf<IProgramacion>;

export type NewRestProgramacion = RestOf<NewProgramacion>;

export type PartialUpdateRestProgramacion = RestOf<PartialUpdateProgramacion>;

export type EntityResponseType = HttpResponse<IProgramacion>;
export type EntityArrayResponseType = HttpResponse<IProgramacion[]>;

@Injectable({ providedIn: 'root' })
export class ProgramacionService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/programacions');

  create(programacion: NewProgramacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(programacion);
    return this.http
      .post<RestProgramacion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(programacion: IProgramacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(programacion);
    return this.http
      .put<RestProgramacion>(`${this.resourceUrl}/${this.getProgramacionIdentifier(programacion)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(programacion: PartialUpdateProgramacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(programacion);
    return this.http
      .patch<RestProgramacion>(`${this.resourceUrl}/${this.getProgramacionIdentifier(programacion)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestProgramacion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestProgramacion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProgramacionIdentifier(programacion: Pick<IProgramacion, 'id'>): number {
    return programacion.id;
  }

  compareProgramacion(o1: Pick<IProgramacion, 'id'> | null, o2: Pick<IProgramacion, 'id'> | null): boolean {
    return o1 && o2 ? this.getProgramacionIdentifier(o1) === this.getProgramacionIdentifier(o2) : o1 === o2;
  }

  addProgramacionToCollectionIfMissing<Type extends Pick<IProgramacion, 'id'>>(
    programacionCollection: Type[],
    ...programacionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const programacions: Type[] = programacionsToCheck.filter(isPresent);
    if (programacions.length > 0) {
      const programacionCollectionIdentifiers = programacionCollection.map(programacionItem =>
        this.getProgramacionIdentifier(programacionItem),
      );
      const programacionsToAdd = programacions.filter(programacionItem => {
        const programacionIdentifier = this.getProgramacionIdentifier(programacionItem);
        if (programacionCollectionIdentifiers.includes(programacionIdentifier)) {
          return false;
        }
        programacionCollectionIdentifiers.push(programacionIdentifier);
        return true;
      });
      return [...programacionsToAdd, ...programacionCollection];
    }
    return programacionCollection;
  }

  protected convertDateFromClient<T extends IProgramacion | NewProgramacion | PartialUpdateProgramacion>(programacion: T): RestOf<T> {
    return {
      ...programacion,
      fechaDesde: programacion.fechaDesde?.format(DATE_FORMAT) ?? null,
      fechaHasta: programacion.fechaHasta?.format(DATE_FORMAT) ?? null,
      desdeHoraAlmuerzo: programacion.desdeHoraAlmuerzo?.toJSON() ?? null,
      hastaHoraAlmuerzo: programacion.hastaHoraAlmuerzo?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restProgramacion: RestProgramacion): IProgramacion {
    return {
      ...restProgramacion,
      fechaDesde: restProgramacion.fechaDesde ? dayjs(restProgramacion.fechaDesde) : undefined,
      fechaHasta: restProgramacion.fechaHasta ? dayjs(restProgramacion.fechaHasta) : undefined,
      desdeHoraAlmuerzo: restProgramacion.desdeHoraAlmuerzo ? dayjs(restProgramacion.desdeHoraAlmuerzo) : undefined,
      hastaHoraAlmuerzo: restProgramacion.hastaHoraAlmuerzo ? dayjs(restProgramacion.hastaHoraAlmuerzo) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestProgramacion>): HttpResponse<IProgramacion> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestProgramacion[]>): HttpResponse<IProgramacion[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
