/* eslint-disable prettier/prettier */
import { Component, inject, OnInit, ChangeDetectorRef, ViewChild, AfterViewInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { HorarioService } from '../horarios-service';
import { IEspecialista } from 'app/entities/especialista/especialista.model';
import SharedModule from 'app/shared/shared.module';
import { Calendar, CalendarOptions, DateSelectArg, EventApi, EventClickArg, EventInput } from '@fullcalendar/core';
import interactionPlugin from '@fullcalendar/interaction';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import listPlugin from '@fullcalendar/list';
import esLocale from '@fullcalendar/core/locales/es';
import moment from 'moment';
import { FullCalendarComponent } from '@fullcalendar/angular';
import { MatDialog } from '@angular/material/dialog';
import { CreacionHorarioComponent } from 'app/shared/dialogos/creacion-horario/creacion-horario.component';

@Component({
  selector: 'jhi-busqueda-horario',
  standalone: true,
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
  templateUrl: './busqueda-horario.component.html',
  styleUrl: './busqueda-horario.component.scss',
})
export class BusquedaHorarioComponent implements OnInit, AfterViewInit {
  changeDetector = inject(ChangeDetectorRef);
  horarioService = inject(HorarioService);
  dialogService = inject(MatDialog);

  eventos: EventInput[] = [];
  especialistas?: IEspecialista[];
  especialidades?: string[];
  // calendario
  currentEvents: EventApi[] = [];

  form: FormGroup = new FormGroup({
    especialidad: new FormControl('', Validators.required),
    especialista: new FormControl('', Validators.required),
    desde: new FormControl(new Date()),
    hasta: new FormControl(new Date()),
  });

  calendarOptions: CalendarOptions = {
    plugins: [interactionPlugin, dayGridPlugin, timeGridPlugin, listPlugin],
    headerToolbar: {
      left: 'prev,next today',
      center: 'title',
      right: 'timeGridWeek,timeGridDay,listWeek',
    },
    initialView: 'timeGridWeek',
    initialEvents: [], // alternatively, use the `events` setting to fetch from a feed
    weekends: true,
    editable: true,
    selectable: true,
    selectMirror: true,
    dayMaxEvents: true,
    // select: this.handleDateSelect.bind(this),
    eventClick: this.handleEventClick.bind(this),
    eventsSet: this.handleEvents.bind(this),
    locale: esLocale,
    navLinks: true,
    datesSet: this.handleDates.bind(this),
    validRange: {
      start: new Date(),
    },
  };
  @ViewChild('calendar', { static: true }) calendar!: FullCalendarComponent;
  calendarApi: any;

  ngAfterViewInit(): void {
    this.calendarApi = this.calendar.getApi();
  }

  ngOnInit(): void {
    this.cargarDatos();
    this.buscar();
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

  handleEventClick(clickInfo: EventClickArg): void {
    // eslint-disable-next-line no-console
    console.log(123);
  }

  handleEvents(events: EventApi[]): void {
    this.currentEvents = events;
    this.changeDetector.detectChanges();
  }

  handleDates(args: any): void {
    const startDate = moment(Date.parse(args.startStr)).format('YYYY-MM-DD');
    const endDate = moment(Date.parse(args.endStr)).add(-1, 'days').format('YYYY-MM-DD');
    const formValues = this.form.value;
  }

  async buscar(): Promise<void> {
    const especialistaId = this.form.value.especialista;
    const desde = this.form.value.desde;
    const hasta = this.form.value.hasta;

    this.eventos = [];

    // eslint-disable-next-line no-console
    console.log(this.form.value);

    const rta = await this.horarioService.buscarTurnos(especialistaId, desde, hasta);
    const body = rta.body ?? null;
    if (body !== null) {
      // eslint-disable-next-line no-console
      console.log(body);
      if (body.length > 0) {
        body.forEach(turno => {
          const data = {
            id: turno.programacionid.toString(),
            title: `${turno.especialidad} ${turno.especialista}`,
            start: turno.desde,
            end: turno.hasta,
            extendedProps: turno,
            backgroundColor: '#34495e',
            borderColor: '#34495e',
          } as EventInput;

          this.eventos = [...this.eventos, data];
        });
      }
      this.calendarOptions.events = this.eventos;
    }
  }

  configurarHorario(): void {
    const especialistaSeleccionado = this.form.value.especialista;

    if (especialistaSeleccionado) {
      const data = this.especialistas?.find(esp => esp.id === especialistaSeleccionado);
      const dialogRef = this.dialogService.open(CreacionHorarioComponent, {
        width: '70%',
        height: 'auto',
        disableClose: true,
        data,
      });
    }
  }
}
