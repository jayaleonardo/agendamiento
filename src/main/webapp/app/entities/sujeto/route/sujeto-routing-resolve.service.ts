import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISujeto } from '../sujeto.model';
import { SujetoService } from '../service/sujeto.service';

const sujetoResolve = (route: ActivatedRouteSnapshot): Observable<null | ISujeto> => {
  const id = route.params.id;
  if (id) {
    return inject(SujetoService)
      .find(id)
      .pipe(
        mergeMap((sujeto: HttpResponse<ISujeto>) => {
          if (sujeto.body) {
            return of(sujeto.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default sujetoResolve;
