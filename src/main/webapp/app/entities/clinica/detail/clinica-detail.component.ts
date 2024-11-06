import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IClinica } from '../clinica.model';

@Component({
  standalone: true,
  selector: 'jhi-clinica-detail',
  templateUrl: './clinica-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ClinicaDetailComponent {
  clinica = input<IClinica | null>(null);

  previousState(): void {
    window.history.back();
  }
}
