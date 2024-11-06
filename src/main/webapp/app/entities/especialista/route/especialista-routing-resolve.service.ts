import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEspecialista } from '../especialista.model';
import { EspecialistaService } from '../service/especialista.service';

const especialistaResolve = (route: ActivatedRouteSnapshot): Observable<null | IEspecialista> => {
  const id = route.params.id;
  if (id) {
    return inject(EspecialistaService)
      .find(id)
      .pipe(
        mergeMap((especialista: HttpResponse<IEspecialista>) => {
          if (especialista.body) {
            return of(especialista.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default especialistaResolve;
