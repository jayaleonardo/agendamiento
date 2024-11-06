import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IHorarioConsulta } from '../horario-consulta.model';
import { HorarioConsultaService } from '../service/horario-consulta.service';

@Component({
  standalone: true,
  templateUrl: './horario-consulta-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class HorarioConsultaDeleteDialogComponent {
  horarioConsulta?: IHorarioConsulta;

  protected horarioConsultaService = inject(HorarioConsultaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.horarioConsultaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
