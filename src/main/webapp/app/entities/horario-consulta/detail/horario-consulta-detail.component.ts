import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IHorarioConsulta } from '../horario-consulta.model';

@Component({
  standalone: true,
  selector: 'jhi-horario-consulta-detail',
  templateUrl: './horario-consulta-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class HorarioConsultaDetailComponent {
  horarioConsulta = input<IHorarioConsulta | null>(null);

  previousState(): void {
    window.history.back();
  }
}
