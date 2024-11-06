import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITipoTerapia } from '../tipo-terapia.model';
import { TipoTerapiaService } from '../service/tipo-terapia.service';

@Component({
  standalone: true,
  templateUrl: './tipo-terapia-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TipoTerapiaDeleteDialogComponent {
  tipoTerapia?: ITipoTerapia;

  protected tipoTerapiaService = inject(TipoTerapiaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoTerapiaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
