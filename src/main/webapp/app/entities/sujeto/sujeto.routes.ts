import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import SujetoResolve from './route/sujeto-routing-resolve.service';

const sujetoRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/sujeto.component').then(m => m.SujetoComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/sujeto-detail.component').then(m => m.SujetoDetailComponent),
    resolve: {
      sujeto: SujetoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/sujeto-update.component').then(m => m.SujetoUpdateComponent),
    resolve: {
      sujeto: SujetoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/sujeto-update.component').then(m => m.SujetoUpdateComponent),
    resolve: {
      sujeto: SujetoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default sujetoRoute;
