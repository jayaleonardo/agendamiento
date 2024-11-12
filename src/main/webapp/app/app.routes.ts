/* eslint-disable prettier/prettier */
import { Routes } from '@angular/router';

import { Authority } from 'app/config/authority.constants';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { errorRoute } from './layouts/error/error.route';

const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./home/home.component'),
    title: 'home.title',
  },
  {
    path: '',
    loadComponent: () => import('./layouts/navbar/navbar.component'),
    outlet: 'navbar',
  },
  {
    path: 'admin',
    data: {
      authorities: [Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
    loadChildren: () => import('./admin/admin.routes'),
  },
  {
    path: 'account',
    loadChildren: () => import('./account/account.route'),
  },
  {
    path: 'login',
    loadComponent: () => import('./login/login.component'),
    title: 'login.title',
  },
  {
    path: 'horarios',
    canActivate: [UserRouteAccessService],
    loadChildren: () => import('./horarios/horarios.routing.module').then(({ HorariosRoutingModule }) => HorariosRoutingModule),
  },
  {
    path: 'agenda',
    canActivate: [UserRouteAccessService],
    loadChildren: () => import('./agenda/agenda.routing.module').then(({ AgendaRoutingModule }) => AgendaRoutingModule),
  },

  {
    path: '',
    loadChildren: () => import(`./entities/entity.routes`),
  },
  ...errorRoute,
];

export default routes;
