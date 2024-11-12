/* eslint-disable prettier/prettier */
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AgendaComponent } from './agenda.component';
@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: '',
        data: {
          title: 'Agenda',
        },
        children: [
          {
            path: '',
            data: { pageTitle: 'Agenda', title: 'Agenda' },
            component: AgendaComponent,
            canActivate: [UserRouteAccessService],
          },
        ],
      },

      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class AgendaRoutingModule {}
