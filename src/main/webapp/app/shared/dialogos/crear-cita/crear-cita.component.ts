import { Component, Inject, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AgendaService } from 'app/agenda/agenda-service';
import { ICitaData } from 'app/agenda/model/citadata.model';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { CheckboxComponent } from 'app/shared/check-box/check-box.component';
import { CheckboxGroupComponent } from 'app/shared/checkbox-group/checkbox-group.component';
import SharedModule from 'app/shared/shared.module';
import { SimpleCheckOptionComponent } from 'app/shared/simple-check-option/simple-check-option.component';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'jhi-crear-cita',
  standalone: true,
  imports: [SharedModule, FormsModule, ReactiveFormsModule, CheckboxGroupComponent, SimpleCheckOptionComponent, CheckboxComponent],
  templateUrl: './crear-cita.component.html',
  styleUrl: './crear-cita.component.scss',
})
export class CrearCitaComponent implements OnInit {
  dialogRef = inject(MatDialogRef<CrearCitaComponent>);
  spinner = inject(NgxSpinnerService);
  agendaService = inject(AgendaService);

  pacientes?: IPaciente[];
  motivos?: any[];
  tipoCita?: any[];
  citaVirtual?: any[];

  form: FormGroup = new FormGroup({
    paciente: new FormControl('', Validators.required),
    motivos: new FormControl('', Validators.required),
    tipoCita: new FormControl(''),
  });

  constructor(@Inject(MAT_DIALOG_DATA) public data: ICitaData) {}
  ngOnInit(): void {
    this.cargarDatos();
  }

  async cargarDatos(): Promise<void> {
    const rta = await this.agendaService.obtenerPacientes();
    const body = rta.body ?? null;
    if (body) {
      this.pacientes = body;
    }

    this.motivos = [];
    this.motivos.push({ id: 'Ansiedad', nombre: 'Ansiedad' });
    this.motivos.push({ id: 'Depresion', nombre: 'Depresión' });
    this.motivos.push({ id: 'Estres', nombre: 'Estrés' });
    this.motivos.push({ id: 'Problemas_pareja', nombre: 'Problemas de pareja' });

    this.tipoCita = [];
    this.tipoCita.push({ id: 'Primera_vez', nombre: 'Primera vez' });
    this.tipoCita.push({ id: 'Visita_control', nombre: 'Visita control' });

    this.citaVirtual = [];
    this.citaVirtual.push({ id: 'Si', nombre: 'Si' });
  }

  cerrarDialogo(): void {
    this.dialogRef.close(null);
  }
}
