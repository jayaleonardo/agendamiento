import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IContacto } from '../contacto.model';
import { ContactoService } from '../service/contacto.service';

@Component({
  standalone: true,
  templateUrl: './contacto-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ContactoDeleteDialogComponent {
  contacto?: IContacto;

  protected contactoService = inject(ContactoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contactoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
