import { Component, Inject, inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ICitaData } from 'app/agenda/model/citadata.model';
import SharedModule from 'app/shared/shared.module';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'jhi-crear-cita',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './crear-cita.component.html',
  styleUrl: './crear-cita.component.scss',
})
export class CrearCitaComponent {
  dialogRef = inject(MatDialogRef<CrearCitaComponent>);
  spinner = inject(NgxSpinnerService);

  constructor(@Inject(MAT_DIALOG_DATA) public data: ICitaData) {}

  cerrarDialogo(): void {
    this.dialogRef.close(null);
  }
}
