import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ITipoTerapia } from '../tipo-terapia.model';

@Component({
  standalone: true,
  selector: 'jhi-tipo-terapia-detail',
  templateUrl: './tipo-terapia-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class TipoTerapiaDetailComponent {
  tipoTerapia = input<ITipoTerapia | null>(null);

  previousState(): void {
    window.history.back();
  }
}
