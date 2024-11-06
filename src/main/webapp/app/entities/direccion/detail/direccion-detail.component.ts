import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IDireccion } from '../direccion.model';

@Component({
  standalone: true,
  selector: 'jhi-direccion-detail',
  templateUrl: './direccion-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class DireccionDetailComponent {
  direccion = input<IDireccion | null>(null);

  previousState(): void {
    window.history.back();
  }
}
