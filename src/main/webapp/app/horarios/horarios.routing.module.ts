/* eslint-disable prettier/prettier */
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BusquedaHorarioComponent } from './busqueda-horario/busqueda-horario.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: '',
        data: {
          title: 'Horarios',
        },
        children: [
          {
            path: '',
            data: { pageTitle: 'Horarios', title: 'Horarios' },
            component: BusquedaHorarioComponent,
            canActivate: [UserRouteAccessService],
          },
        ],
      },

      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class HorariosRoutingModule {}
