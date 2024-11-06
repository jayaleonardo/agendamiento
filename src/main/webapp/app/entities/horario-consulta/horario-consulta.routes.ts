import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import HorarioConsultaResolve from './route/horario-consulta-routing-resolve.service';

const horarioConsultaRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/horario-consulta.component').then(m => m.HorarioConsultaComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/horario-consulta-detail.component').then(m => m.HorarioConsultaDetailComponent),
    resolve: {
      horarioConsulta: HorarioConsultaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/horario-consulta-update.component').then(m => m.HorarioConsultaUpdateComponent),
    resolve: {
      horarioConsulta: HorarioConsultaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/horario-consulta-update.component').then(m => m.HorarioConsultaUpdateComponent),
    resolve: {
      horarioConsulta: HorarioConsultaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default horarioConsultaRoute;
