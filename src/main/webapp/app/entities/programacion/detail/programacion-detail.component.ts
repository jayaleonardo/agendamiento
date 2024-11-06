import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IProgramacion } from '../programacion.model';

@Component({
  standalone: true,
  selector: 'jhi-programacion-detail',
  templateUrl: './programacion-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ProgramacionDetailComponent {
  programacion = input<IProgramacion | null>(null);

  previousState(): void {
    window.history.back();
  }
}
