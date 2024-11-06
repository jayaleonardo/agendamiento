import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITipoTerapia } from '../tipo-terapia.model';
import { TipoTerapiaService } from '../service/tipo-terapia.service';

const tipoTerapiaResolve = (route: ActivatedRouteSnapshot): Observable<null | ITipoTerapia> => {
  const id = route.params.id;
  if (id) {
    return inject(TipoTerapiaService)
      .find(id)
      .pipe(
        mergeMap((tipoTerapia: HttpResponse<ITipoTerapia>) => {
          if (tipoTerapia.body) {
            return of(tipoTerapia.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default tipoTerapiaResolve;
