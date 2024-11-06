import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHorarioConsulta } from '../horario-consulta.model';
import { HorarioConsultaService } from '../service/horario-consulta.service';

const horarioConsultaResolve = (route: ActivatedRouteSnapshot): Observable<null | IHorarioConsulta> => {
  const id = route.params.id;
  if (id) {
    return inject(HorarioConsultaService)
      .find(id)
      .pipe(
        mergeMap((horarioConsulta: HttpResponse<IHorarioConsulta>) => {
          if (horarioConsulta.body) {
            return of(horarioConsulta.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default horarioConsultaResolve;
