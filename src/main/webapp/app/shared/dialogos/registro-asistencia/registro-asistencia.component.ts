import { Component, Inject, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ICitaData } from 'app/agenda/model/citadata.model';
import SharedModule from 'app/shared/shared.module';

@Component({
  selector: 'jhi-registro-asistencia',
  standalone: true,
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
  templateUrl: './registro-asistencia.component.html',
  styleUrl: './registro-asistencia.component.scss',
})
export class RegistroAsistenciaComponent implements OnInit {
  dialogRef = inject(MatDialogRef<RegistroAsistenciaComponent>);

  form: FormGroup = new FormGroup({
    tareas: new FormControl(''),
  });

  constructor(@Inject(MAT_DIALOG_DATA) public data: ICitaData) {}

  ngOnInit(): void {
    // eslint-disable-next-line no-console
    console.log(123);
  }

  guardar(): void {
    // eslint-disable-next-line no-console
    console.log(123);
  }

  cerrarDialogo(): void {
    this.dialogRef.close(null);
  }
}
