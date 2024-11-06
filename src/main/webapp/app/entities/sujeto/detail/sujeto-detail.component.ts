import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ISujeto } from '../sujeto.model';

@Component({
  standalone: true,
  selector: 'jhi-sujeto-detail',
  templateUrl: './sujeto-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class SujetoDetailComponent {
  sujeto = input<ISujeto | null>(null);

  previousState(): void {
    window.history.back();
  }
}
