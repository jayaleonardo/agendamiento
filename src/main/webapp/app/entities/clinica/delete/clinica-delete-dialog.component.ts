import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IClinica } from '../clinica.model';
import { ClinicaService } from '../service/clinica.service';

@Component({
  standalone: true,
  templateUrl: './clinica-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ClinicaDeleteDialogComponent {
  clinica?: IClinica;

  protected clinicaService = inject(ClinicaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.clinicaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
