import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { IEspecialista } from 'app/entities/especialista/especialista.model';
import { HorarioService } from 'app/horarios/horarios-service';
import SharedModule from 'app/shared/shared.module';
import { NgxSpinnerService } from 'ngx-spinner';
import { AgendaService } from './agenda-service';
import { ICitaData } from './model/citadata.model';
import moment from 'moment';
import { MatDialog } from '@angular/material/dialog';
import { CrearCitaComponent } from 'app/shared/dialogos/crear-cita/crear-cita.component';
import { lastValueFrom } from 'rxjs';
import { RegistroAsistenciaComponent } from 'app/shared/dialogos/registro-asistencia/registro-asistencia.component';
import Swal from 'sweetalert2';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'jhi-agenda',
  standalone: true,
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
  templateUrl: './agenda.component.html',
  styleUrl: './agenda.component.scss',
})
export class AgendaComponent implements OnInit {
  horarioService = inject(HorarioService);
  agendaService = inject(AgendaService);
  spinner = inject(NgxSpinnerService);
  dialogService = inject(MatDialog);
  toastr = inject(ToastrService);

  especialistas?: IEspecialista[];
  especialidades?: string[];
  estados?: any[];
  citas?: ICitaData[];

  form: FormGroup = new FormGroup({
    especialidad: new FormControl(''),
    especialista: new FormControl(''),
    desde: new FormControl(new Date(), Validators.required),
    hasta: new FormControl(new Date(), Validators.required),
    estado: new FormControl(),
    paciente: new FormControl(),
  });

  ngOnInit(): void {
    this.cargarDatos();
  }
  async cargarDatos(): Promise<void> {
    const rta = await this.horarioService.buscarEspecialidades();
    const body = rta.body ?? null;
    if (body !== null) {
      this.especialidades = body;
      if (this.especialidades.length > 0) {
        // fijar un valor por defecto de especialidad
        this.form.patchValue({ especialidad: this.especialidades[0] });
        this.buscarEspecialista(this.especialidades[0]);
      }
    }

    const hastaFecha = new Date();
    hastaFecha.setDate(hastaFecha.getDate() + 7);
    this.form.patchValue({ hasta: hastaFecha });

    this.estados = [
      { id: 'Reserva', nombre: 'Reserva en línea' },
      { id: 'Disponible', nombre: 'Disponible' },
      { id: 'Cita_asignada', nombre: 'Cita asignada' },
      { id: 'Confirmado', nombre: 'Confirmado paciente' },
      { id: 'Atendida', nombre: 'Atendida' },
    ];
  }

  async buscarEspecialista(especialidad: string): Promise<void> {
    // eslint-disable-next-line no-console
    console.log(especialidad);
    const rta = await this.horarioService.especialistasPorEspecialidad(especialidad);
    const body = rta.body ?? null;
    if (body !== null) {
      this.especialistas = body;
    }
  }

  async buscar(): Promise<void> {
    this.spinner.show();
    const desde = moment(this.form.value.desde).format('YYYY-MM-DD');
    const hasta = moment(this.form.value.hasta).format('YYYY-MM-DD');
    const especialidad = this.form.value.especialidad;
    const especialista = this.form.value.especialista;
    const estado = this.form.value.estado;
    const paciente = this.form.value.paciente;

    const rta = await this.agendaService.consultarCitas(desde, hasta, especialidad, especialista, estado, paciente);
    const body = rta.body ?? null;
    if (body) {
      this.citas = body;
    }
    this.spinner.hide();
  }

  async asignar(data: ICitaData): Promise<void> {
    const dialogRef = this.dialogService.open(CrearCitaComponent, {
      width: '60%',
      height: 'auto',
      disableClose: true,
      data,
    });

    const resultadoDialogo = await lastValueFrom(dialogRef.afterClosed());
    if (resultadoDialogo !== null) {
      this.buscar();
    }
  }

  async atencion(data: ICitaData): Promise<void> {
    const dialogRef = this.dialogService.open(RegistroAsistenciaComponent, {
      width: '60%',
      height: 'auto',
      disableClose: true,
      data,
    });

    const resultadoDialogo = await lastValueFrom(dialogRef.afterClosed());
    if (resultadoDialogo !== null) {
      this.buscar();
    }
  }

  confirmar(cita: ICitaData): void {
    Swal.fire({
      title: 'Confirmación de cita',
      text: 'Desea confirmar la cita?!',
      icon: 'info',
      showCancelButton: true,
      confirmButtonText: 'Si',
      cancelButtonText: 'No',
    }).then(async result => {
      if (result.value) {
        this.spinner.show();
        // cambiar estado de la cita
        const rta = await this.agendaService.cambiarEstado(cita.citaid!, 'Confirmada');
        const body = rta.body ?? null;
        if (body) {
          this.toastr.success('Confirmación realizada', 'Aviso');
          this.spinner.hide();
          this.buscar();
        } else {
          this.toastr.error('Error en la actualizacio1n', 'Error');
          this.spinner.hide();
        }
      }
    });
  }

  cancelar(cita: ICitaData): void {
    Swal.fire({
      title: 'Cancelar de cita',
      text: 'Desea cancelar la cita?!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si',
      cancelButtonText: 'No',
    }).then(async result => {
      if (result.value) {
        this.spinner.show();
        // cambiar estado de la cita
        const rta = await this.agendaService.cambiarEstado(cita.citaid!, 'Cancelada');
        const body = rta.body ?? null;
        if (body) {
          this.toastr.success('Cancelación realizada', 'Aviso');
          this.spinner.hide();
          this.buscar();
        } else {
          this.toastr.error('Error en la actualización', 'Error');
          this.spinner.hide();
        }
      }
    });
  }
}
