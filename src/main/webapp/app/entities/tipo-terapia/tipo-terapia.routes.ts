import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import TipoTerapiaResolve from './route/tipo-terapia-routing-resolve.service';

const tipoTerapiaRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/tipo-terapia.component').then(m => m.TipoTerapiaComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/tipo-terapia-detail.component').then(m => m.TipoTerapiaDetailComponent),
    resolve: {
      tipoTerapia: TipoTerapiaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/tipo-terapia-update.component').then(m => m.TipoTerapiaUpdateComponent),
    resolve: {
      tipoTerapia: TipoTerapiaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/tipo-terapia-update.component').then(m => m.TipoTerapiaUpdateComponent),
    resolve: {
      tipoTerapia: TipoTerapiaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default tipoTerapiaRoute;
