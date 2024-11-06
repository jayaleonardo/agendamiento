import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ClinicaResolve from './route/clinica-routing-resolve.service';

const clinicaRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/clinica.component').then(m => m.ClinicaComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/clinica-detail.component').then(m => m.ClinicaDetailComponent),
    resolve: {
      clinica: ClinicaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/clinica-update.component').then(m => m.ClinicaUpdateComponent),
    resolve: {
      clinica: ClinicaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/clinica-update.component').then(m => m.ClinicaUpdateComponent),
    resolve: {
      clinica: ClinicaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default clinicaRoute;
