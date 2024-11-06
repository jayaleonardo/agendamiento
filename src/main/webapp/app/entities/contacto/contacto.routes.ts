import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ContactoResolve from './route/contacto-routing-resolve.service';

const contactoRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/contacto.component').then(m => m.ContactoComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/contacto-detail.component').then(m => m.ContactoDetailComponent),
    resolve: {
      contacto: ContactoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/contacto-update.component').then(m => m.ContactoUpdateComponent),
    resolve: {
      contacto: ContactoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/contacto-update.component').then(m => m.ContactoUpdateComponent),
    resolve: {
      contacto: ContactoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default contactoRoute;
