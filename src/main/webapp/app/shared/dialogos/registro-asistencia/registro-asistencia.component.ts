import { Component, Inject, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AgendaService } from 'app/agenda/agenda-service';
import { ICitaData } from 'app/agenda/model/citadata.model';
import SharedModule from 'app/shared/shared.module';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'jhi-registro-asistencia',
  standalone: true,
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
  templateUrl: './registro-asistencia.component.html',
  styleUrl: './registro-asistencia.component.scss',
})
export class RegistroAsistenciaComponent implements OnInit {
  dialogRef = inject(MatDialogRef<RegistroAsistenciaComponent>);
  spinner = inject(NgxSpinnerService);
  agendaService = inject(AgendaService);

  form: FormGroup = new FormGroup({
    tareas: new FormControl(''),
  });

  constructor(@Inject(MAT_DIALOG_DATA) public data: ICitaData) {}

  ngOnInit(): void {
    // eslint-disable-next-line no-console
    console.log(123);
  }

  atendido(): void {
    this.guardar('Atendido');
  }

  noAsistio(): void {
    this.guardar('No_asistio');
  }

  async guardar(asistencia: string): Promise<void> {
    this.spinner.show();
    const task = this.form.value.tarea;
    const datos = {
      citaId: this.data.citaid,
      tarea: task,
      estado: asistencia,
    };
    const rta = await this.agendaService.registrarAsistencia(datos);
    const body = rta.body ?? null;
    this.dialogRef.close(body);

    this.spinner.hide();
  }

  cerrarDialogo(): void {
    this.dialogRef.close(null);
  }
}
