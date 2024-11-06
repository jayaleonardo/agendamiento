import dayjs from 'dayjs/esm';

import { IHorarioConsulta, NewHorarioConsulta } from './horario-consulta.model';

export const sampleWithRequiredData: IHorarioConsulta = {
  id: 14501,
  fechaHorario: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T21:17'),
  duracionMinutos: 32527,
};

export const sampleWithPartialData: IHorarioConsulta = {
  id: 31321,
  fechaHorario: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T04:04'),
  duracionMinutos: 3715,
  diaSemana: 'because misguided',
  esHorarioAtencion: false,
};

export const sampleWithFullData: IHorarioConsulta = {
  id: 18606,
  fechaHorario: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T12:00'),
  duracionMinutos: 2509,
  diaSemana: 'butter longingly',
  esHorarioAtencion: false,
  estado: 'bank which',
};

export const sampleWithNewData: NewHorarioConsulta = {
  fechaHorario: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T02:58'),
  duracionMinutos: 21923,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
