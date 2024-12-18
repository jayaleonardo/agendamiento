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
  estadosCita?: any[];
  canales?: any[];
  recordatorios?: any[];
  isSwitchOn = false;
  motivoSwitch = false;
  estado?: string = '';

  form: FormGroup = new FormGroup({
    paciente: new FormControl('', Validators.required),
    motivos: new FormControl('', Validators.required),
    motivoDetalle: new FormControl({ value: '', disabled: true }),
    tipoCita: new FormControl(''),
    virtual: new FormControl(''),
    detallevirtual: new FormControl({ value: '', disabled: true }),
    canal: new FormControl('', Validators.required),
    recordatorio: new FormControl('', Validators.required),
    observacion: new FormControl(''),
    infoReserva: new FormControl({ value: '', disabled: true }),
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
    this.motivos.push({ id: 'Otro', nombre: 'Otro' });

    this.tipoCita = [];
    this.tipoCita.push({ id: 'Primera_vez', nombre: 'Primera vez' });
    this.tipoCita.push({ id: 'Visita_control', nombre: 'Visita control' });

    this.citaVirtual = [];
    this.citaVirtual.push({ id: 'Si', nombre: 'Si' });

    this.canales = [];
    this.canales.push({ id: 'Cita_online', nombre: 'Cita online' });
    this.canales.push({ id: 'Marketing_directo', nombre: 'Marketing directo' });
    this.canales.push({ id: 'Referidos', nombre: 'Referidos' });

    this.recordatorios = [];
    this.recordatorios.push({ id: 'Correo_electronico', nombre: 'Correo electrónico' });
    this.recordatorios.push({ id: 'SMS/Whatsapp', nombre: 'SMS/Whatspp' });

    this.estadosCita = [];
    this.estadosCita.push({ id: 'Disponible', nombre: 'Disponible' });
    this.estadosCita.push({ id: 'Confirmada', nombre: 'Confirmada' });
    this.estadosCita.push({ id: 'Reagendada', nombre: 'Reagendada' });
    this.estadosCita.push({ id: 'Reservada_linea', nombre: 'Reservada en línea' });
    this.estadosCita.push({ id: 'Atendida', nombre: 'Atendida' });
    this.estadosCita.push({ id: 'Cancelada', nombre: 'Cancelada' });

    // en el caso que exista informacion de reserva colocar en la ui
    this.form.patchValue({ infoReserva: this.data.inforeserva });
    this.estado = this.data.estado!;

    // eslint-disable-next-line no-console
    console.log(this.estado);
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-condition
    if (this.estado === null) {
      this.estado = 'Disponible';
    }
  }

  cerrarDialogo(): void {
    this.dialogRef.close(null);
  }

  onCheckTipoCita(value: any[]): void {
    if (value.length > 0) {
      this.form.controls.detallevirtual.enable();
    } else {
      this.form.controls.detallevirtual.disable();
    }
  }

  onCheckMotivo(value: any[]): void {
    if (value.length > 0) {
      const otro = value.find(val => val === 'Otro');

      // eslint-disable-next-line no-console
      console.log(otro);
      if (otro) {
        // this.form.controls.motivoDetalle.enable();
        this.form.get('motivoDetalle')?.enable();
      }
    } else {
      // this.form.controls.motivoDetalle.disable();
      this.form.get('motivoDetalle')?.disable();
    }
  }

  async guardar(): Promise<void> {
    this.spinner.show();
    const canales = (this.form.value.canal as []).join(',');
    const motivos = (this.form.value.motivos as []).join(',');
    const tipoVisitas = (this.form.value.tipoCita as []).join(',');
    let esVirtual = false;
    if (this.form.value.virtual !== '') {
      esVirtual = true;
    }

    const datos = {
      pacienteId: this.form.value.paciente,
      motivoConsulta: motivos,
      motivoDetalle: this.form.value.motivoDetalle,
      virtual: esVirtual,
      detallevirtual: this.form.value.detallevirtual,
      canalAtencion: canales,
      recordatorio: this.form.value.recordatorio,
      observacion: this.form.value.observacion,
      fechaCita: this.data.fecha,
      horaInicio: this.data.horainicio,
      horaFin: this.data.horarioFin,
      programacionId: this.data.id,
      citaId: this.data.citaid,
      tipoVisita: tipoVisitas,
    };

    // eslint-disable-next-line no-console
    console.log(datos);

    const rta = await this.agendaService.guardarCita(datos);
    const body = rta.body ?? null;
    this.dialogRef.close(body);
    this.spinner.hide();
  }
}
