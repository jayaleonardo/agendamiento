/* eslint-disable prettier/prettier */
import { Component, inject, OnInit, ChangeDetectorRef, ViewChild } from '@angular/core';
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

@Component({
  selector: 'jhi-busqueda-horario',
  standalone: true,
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
  templateUrl: './busqueda-horario.component.html',
  styleUrl: './busqueda-horario.component.scss',
})
export class BusquedaHorarioComponent implements OnInit {
  changeDetector = inject(ChangeDetectorRef);
  horarioService = inject(HorarioService);

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

  // eslint-disable-next-line @typescript-eslint/no-misused-promises
  async ngOnInit(): Promise<void> {
    const rta = await this.horarioService.buscarEspecialidades();
    const body = rta.body ?? null;
    if (body !== null) {
      this.especialidades = body;
    }
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

  buscar(): void {
    const especialista = this.form.value.especialista;
    const desde = this.form.value.desde;
    const hasta = this.form.value.hasta;

    // realizar busqueda en horarios
  }
}
