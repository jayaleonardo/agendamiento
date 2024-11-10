import dayjs from 'dayjs/esm';

import { IHorarioConsulta, NewHorarioConsulta } from './horario-consulta.model';

export const sampleWithRequiredData: IHorarioConsulta = {
  id: 8567,
  desde: dayjs('2024-11-05'),
  hasta: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T22:42'),
  horaFin: dayjs('2024-11-05T16:28'),
  duracionMinutos: 5895,
};

export const sampleWithPartialData: IHorarioConsulta = {
  id: 15510,
  desde: dayjs('2024-11-05'),
  hasta: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T01:51'),
  horaFin: dayjs('2024-11-05T07:08'),
  duracionMinutos: 8324,
  diaSemana: 'possible generously where',
  desdeHoraAlmuerzo: dayjs('2024-11-05T03:42'),
  hastaHoraAlmuerzo: dayjs('2024-11-05T03:35'),
};

export const sampleWithFullData: IHorarioConsulta = {
  id: 14911,
  desde: dayjs('2024-11-05'),
  hasta: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T00:08'),
  horaFin: dayjs('2024-11-05T17:08'),
  duracionMinutos: 22430,
  diaSemana: 'enormously for oof',
  esHorarioAtencion: true,
  estado: 'until',
  desdeHoraAlmuerzo: dayjs('2024-11-05T01:27'),
  hastaHoraAlmuerzo: dayjs('2024-11-05T11:54'),
};

export const sampleWithNewData: NewHorarioConsulta = {
  desde: dayjs('2024-11-05'),
  hasta: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T16:16'),
  horaFin: dayjs('2024-11-05T13:13'),
  duracionMinutos: 7676,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
