import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ProgramacionResolve from './route/programacion-routing-resolve.service';

const programacionRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/programacion.component').then(m => m.ProgramacionComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/programacion-detail.component').then(m => m.ProgramacionDetailComponent),
    resolve: {
      programacion: ProgramacionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/programacion-update.component').then(m => m.ProgramacionUpdateComponent),
    resolve: {
      programacion: ProgramacionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/programacion-update.component').then(m => m.ProgramacionUpdateComponent),
    resolve: {
      programacion: ProgramacionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default programacionRoute;
