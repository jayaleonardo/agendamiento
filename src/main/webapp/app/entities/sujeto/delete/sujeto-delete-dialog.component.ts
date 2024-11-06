import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISujeto } from '../sujeto.model';
import { SujetoService } from '../service/sujeto.service';

@Component({
  standalone: true,
  templateUrl: './sujeto-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SujetoDeleteDialogComponent {
  sujeto?: ISujeto;

  protected sujetoService = inject(SujetoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sujetoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
