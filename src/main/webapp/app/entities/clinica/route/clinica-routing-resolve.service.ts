import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IClinica } from '../clinica.model';
import { ClinicaService } from '../service/clinica.service';

const clinicaResolve = (route: ActivatedRouteSnapshot): Observable<null | IClinica> => {
  const id = route.params.id;
  if (id) {
    return inject(ClinicaService)
      .find(id)
      .pipe(
        mergeMap((clinica: HttpResponse<IClinica>) => {
          if (clinica.body) {
            return of(clinica.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default clinicaResolve;
