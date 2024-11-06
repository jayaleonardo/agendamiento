import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IProgramacion } from '../programacion.model';
import { ProgramacionService } from '../service/programacion.service';

@Component({
  standalone: true,
  templateUrl: './programacion-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ProgramacionDeleteDialogComponent {
  programacion?: IProgramacion;

  protected programacionService = inject(ProgramacionService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.programacionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
