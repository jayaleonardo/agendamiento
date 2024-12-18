import { CommonModule } from '@angular/common';
import { Component, inject, Inject } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { BrowserModule } from '@angular/platform-browser';
import { IEspecialista } from 'app/entities/especialista/especialista.model';
import { HorarioService } from 'app/horarios/horarios-service';
import { CheckboxComponent } from 'app/shared/check-box/check-box.component';
import { CheckboxGroupComponent } from 'app/shared/checkbox-group/checkbox-group.component';
import SharedModule from 'app/shared/shared.module';
import { SimpleCheckOptionComponent } from 'app/shared/simple-check-option/simple-check-option.component';
import moment from 'moment';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'jhi-creacion-horario',
  standalone: true,
  imports: [SharedModule, FormsModule, ReactiveFormsModule, CheckboxGroupComponent, SimpleCheckOptionComponent, CheckboxComponent],
  templateUrl: './creacion-horario.component.html',
  styleUrl: './creacion-horario.component.scss',
})
export class CreacionHorarioComponent {
  horarioService = inject(HorarioService);
  dialogRef = inject(MatDialogRef<CreacionHorarioComponent>);
  spinner = inject(NgxSpinnerService);

  duraciones: number[] = [20, 30, 45, 60, 90, 120];
  diasSemana: any[];
  diasSeleccionados?: any[];
  mostrarConfiguracion = false;

  // datos antes ?de guardar y que se muestran al usuario
  preHorarioDesde?: any;
  preHorarioHasta?: any;
  preHorarioAlmuerzoDesde?: any;
  preHorarioAlmuerzoHasta?: any;
  preDuracion?: number;
  numeroTurnos = 0;

  form: FormGroup = new FormGroup({
    desde: new FormControl(new Date(), Validators.required),
    hasta: new FormControl(new Date(), Validators.required),
    duracion: new FormControl('', Validators.required),
    horarioDesde: new FormControl(new Date(), Validators.required),
    horarioHasta: new FormControl(new Date(), Validators.required),
    almuerzoDesde: new FormControl(''),
    almuerzoHasta: new FormControl(''),
    diasSeleccionados: new FormControl(''),
  });

  constructor(@Inject(MAT_DIALOG_DATA) public data: IEspecialista) {
    this.diasSemana = [];
    this.diasSemana.push({ id: 1, nombre: 'Lunes' });
    this.diasSemana.push({ id: 2, nombre: 'Martes' });
    this.diasSemana.push({ id: 3, nombre: 'Miércoles' });
    this.diasSemana.push({ id: 4, nombre: 'Jueves' });
    this.diasSemana.push({ id: 5, nombre: 'Viernes' });
    this.diasSemana.push({ id: 6, nombre: 'Sábado' });
    this.diasSemana.push({ id: 7, nombre: 'Domingo' });
  }

  onCheck(value: any[]): void {
    // nada que hacer
    // eslint-disable-next-line no-console
    console.log(1);
  }

  aplicarConfiguracion(): void {
    // obtener los variables de configuracion
    const desde = this.form.value.desde;
    const hasta = this.form.value.hasta;
    this.preDuracion = this.form.value.duracion;
    this.preHorarioDesde = moment(this.form.value.horarioDesde).format('HH:mm');
    this.preHorarioHasta = moment(this.form.value.horarioHasta).format('HH:mm');
    this.diasSeleccionados = this.form.value.diasSeleccionados;

    this.preHorarioAlmuerzoDesde = moment(this.form.value.almuerzoDesde).format('HH:mm');
    this.preHorarioAlmuerzoHasta = moment(this.form.value.almuerzoHasta).format('HH:mm');

    this.onOkAlmuerzo();
    this.mostrarConfiguracion = true;
  }

  nombreDia(dia: number): string {
    // eslint-disable-next-line @typescript-eslint/no-unsafe-return
    return this.diasSemana.find(day => day.id === dia).nombre;
  }

  async crear(): Promise<void> {
    const desde = this.form.value.desde;
    const hasta = this.form.value.hasta;
    const duracion = this.form.value.duracion;
    this.spinner.show();
    if (this.numeroTurnos > 0) {
      const rta = await this.horarioService.crearProgramacion(
        desde,
        hasta,
        this.preHorarioDesde,
        this.preHorarioHasta,
        this.preHorarioAlmuerzoDesde,
        this.preHorarioAlmuerzoHasta,
        duracion,
        this.diasSeleccionados!.join(','),
        this.data.id,
        this.numeroTurnos,
      );

      const body = rta.body ?? null;
      if (body) {
        this.dialogRef.close(body);
        this.spinner.hide();
      }
    }
    this.spinner.hide();
  }

  onOkAlmuerzo(): void {
    const tiempoConsulta = this.form.value.duracion;

    if (tiempoConsulta !== null || tiempoConsulta !== '') {
      const momentStartTime = moment(this.form.value.horarioDesde, 'HH:mm');
      const momentEndTime = moment(this.form.value.horarioHasta, 'HH:mm');

      const momentStartTimeAlmuerzo = moment(this.form.value.almuerzoDesde, 'HH:mm');
      const momentEndTimeAlmuerzo = moment(this.form.value.almuerzoHasta, 'HH:mm');
      const durationAntesAlmuerzo = moment.duration(momentStartTimeAlmuerzo.diff(momentStartTime));
      const durationDespuesAlmuerzo = moment.duration(momentEndTime.diff(momentEndTimeAlmuerzo));

      const numeroTurnosAntesAlmuerzo = Math.trunc(durationAntesAlmuerzo.asMinutes() / tiempoConsulta);
      const numeroTurnosDespuesAlmuerzo = Math.trunc(durationDespuesAlmuerzo.asMinutes() / tiempoConsulta);

      this.numeroTurnos = numeroTurnosAntesAlmuerzo + numeroTurnosDespuesAlmuerzo;
    }
  }

  cerrarDialogo(): void {
    this.dialogRef.close(null);
  }
}
