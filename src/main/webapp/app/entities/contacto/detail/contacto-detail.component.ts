import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IContacto } from '../contacto.model';

@Component({
  standalone: true,
  selector: 'jhi-contacto-detail',
  templateUrl: './contacto-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ContactoDetailComponent {
  contacto = input<IContacto | null>(null);

  previousState(): void {
    window.history.back();
  }
}
