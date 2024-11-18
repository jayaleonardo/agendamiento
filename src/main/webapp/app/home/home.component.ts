import { Component, OnDestroy, OnInit, ViewChild, inject, model, signal } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { IEspecialista } from 'app/entities/especialista/especialista.model';
import { HorarioService } from 'app/horarios/horarios-service';
import { HomeService } from './home-service';
import moment from 'moment';
import { NgxSpinnerService } from 'ngx-spinner';
import { ITurnoDisponible } from './turnos.model';
import { CheckboxGroupComponent } from 'app/shared/checkbox-group/checkbox-group.component';
import { SimpleCheckOptionComponent } from 'app/shared/simple-check-option/simple-check-option.component';
import { CheckboxComponent } from 'app/shared/check-box/check-box.component';
import { MatStepper } from '@angular/material/stepper';
import { ToastrService } from 'ngx-toastr';

@Component({
  standalone: true,
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  imports: [
    SharedModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    CheckboxGroupComponent,
    SimpleCheckOptionComponent,
    CheckboxComponent,
  ],
})
export default class HomeComponent implements OnInit, OnDestroy {
  homeService = inject(HomeService);
  spinner = inject(NgxSpinnerService);
  account = signal<Account | null>(null);
  toastr = inject(ToastrService);

  foto = 'https://cdn-icons-png.flaticon.com/512/9193/9193824.png';
  especialistas?: IEspecialista[];
  especialidades?: string[];

  especialidadSeleccionada?: string;
  especialistaSelecionado?: IEspecialista | null;

  fechaSeleccionada = model<Date | null>();
  minDate = new Date();
  fechaAux?: string;
  turnosDisponibles?: ITurnoDisponible[] | [];
  turnoSeleccionado?: ITurnoDisponible | null;
  continuarPaso2 = true;

  citaVirtual?: any[];

  @ViewChild('stepper')
  stepper!: MatStepper;

  form1: FormGroup = new FormGroup({
    especialidad: new FormControl('', Validators.required),
    especialista: new FormControl('', Validators.required),
  });

  form3: FormGroup = new FormGroup({
    nombre: new FormControl('', Validators.required),
    segundoNombre: new FormControl(''),
    apellido: new FormControl('', Validators.required),
    segundoApellido: new FormControl(''),
    prefijo: new FormControl(''),
    celular: new FormControl('', Validators.required),
    videoconferencia: new FormControl(''),
  });

  private readonly destroy$ = new Subject<void>();
  private readonly accountService = inject(AccountService);
  private readonly router = inject(Router);

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => this.account.set(account));

    this.cargarDatos();
  }

  async cargarDatos(): Promise<void> {
    const rta = await this.homeService.buscarEspecialidades();
    const body = rta.body ?? null;
    if (body !== null) {
      this.especialidades = body;
      if (this.especialidades.length > 0) {
        // fijar un valor por defecto de especialidad
        this.form1.patchValue({ especialidad: this.especialidades[0] });
        this.buscarEspecialista(this.especialidades[0]);
      }
    }

    this.citaVirtual = [];
    this.citaVirtual.push({ id: 'virtual', nombre: 'Deseo por Videoconferencia' });

    this.continuarPaso2 = true;
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  async buscarEspecialista(especialidad: string): Promise<void> {
    // eslint-disable-next-line no-console
    console.log(especialidad);
    const rta = await this.homeService.especialistasPorEspecialidad(especialidad);
    const body = rta.body ?? null;
    if (body !== null) {
      this.especialistas = body;
    }
    this.fotoPorDefecto();
  }

  fotoPorDefecto(): void {
    this.foto = 'https://cdn-icons-png.flaticon.com/512/9193/9193824.png';
  }

  async buscarFoto(especialistaid: number): Promise<void> {
    const rta = await this.homeService.buscarFoto(especialistaid);
    const body = rta.body ?? null;
    this.fotoPorDefecto();
    if (body !== null) {
      if (body.fotoUrl !== '') {
        this.foto = body.fotoUrl!;
      }
    }
  }

  obtenerDatos(): void {
    this.especialidadSeleccionada = this.form1.value.especialidad;
    const especialistaID = this.form1.value.especialista;
    this.especialistaSelecionado = this.especialistas?.find(esp => esp.id === especialistaID);
  }

  async seleccionarFecha(event: Date): Promise<void> {
    // eslint-disable-next-line no-console
    console.log(event);
    this.fechaAux = moment(event).format('YYYY-MM-DD');

    this.spinner.show();
    const rta = await this.homeService.turnosDisponibles(event);
    const body = rta.body ?? null;
    if (body !== null) {
      this.turnosDisponibles = body;
    }
    this.spinner.hide();
  }

  fijarTurno(turno: ITurnoDisponible): void {
    this.turnoSeleccionado = turno;
    this.continuarPaso2 = false;
  }

  async guardar(): Promise<void> {
    const parametros = {
      nombre: this.form3.value.nombre,
      segundoNombre: this.form3.value.segundoNombre,
      apellido: this.form3.value.apellido,
      segundoApellido: this.form3.value.segundoApellido,
      celular: `${this.form3.value.prefijo} ${this.form3.value.celular}`,
      turnoId: this.turnoSeleccionado?.id,
      virtual: this.form3.value.videoconferencia !== '' && this.form3.value.videoconferencia.length > 0,
    };

    const rta = await this.homeService.guardarPreReserva(parametros);
    const body = rta.body ?? null;
    if (body) {
      this.form3.reset();
      this.turnoSeleccionado = null;
      this.especialistaSelecionado = null;
      this.stepper.selectedIndex = 0;
      this.cargarDatos();
      this.toastr.success('Pre reserva guardada', 'Aviso');
    } else {
      this.toastr.error('Error de guardado', 'Error');
    }
  }
}
