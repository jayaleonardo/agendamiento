import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContacto } from '../contacto.model';
import { ContactoService } from '../service/contacto.service';

const contactoResolve = (route: ActivatedRouteSnapshot): Observable<null | IContacto> => {
  const id = route.params.id;
  if (id) {
    return inject(ContactoService)
      .find(id)
      .pipe(
        mergeMap((contacto: HttpResponse<IContacto>) => {
          if (contacto.body) {
            return of(contacto.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default contactoResolve;
