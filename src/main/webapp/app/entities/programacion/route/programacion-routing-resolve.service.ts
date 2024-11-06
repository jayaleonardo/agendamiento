import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProgramacion } from '../programacion.model';
import { ProgramacionService } from '../service/programacion.service';

const programacionResolve = (route: ActivatedRouteSnapshot): Observable<null | IProgramacion> => {
  const id = route.params.id;
  if (id) {
    return inject(ProgramacionService)
      .find(id)
      .pipe(
        mergeMap((programacion: HttpResponse<IProgramacion>) => {
          if (programacion.body) {
            return of(programacion.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default programacionResolve;
