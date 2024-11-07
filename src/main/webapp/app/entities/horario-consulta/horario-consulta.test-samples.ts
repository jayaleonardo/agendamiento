import dayjs from 'dayjs/esm';

import { IHorarioConsulta, NewHorarioConsulta } from './horario-consulta.model';

export const sampleWithRequiredData: IHorarioConsulta = {
  id: 29383,
  fechaHorario: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T06:02'),
  duracionMinutos: 11318,
};

export const sampleWithPartialData: IHorarioConsulta = {
  id: 19198,
  fechaHorario: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T03:15'),
  duracionMinutos: 9035,
  desdeHoraAlmuerzo: dayjs('2024-11-05T11:07'),
  hastaHoraAlmuerzo: dayjs('2024-11-05T14:45'),
};

export const sampleWithFullData: IHorarioConsulta = {
  id: 3750,
  fechaHorario: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T07:08'),
  duracionMinutos: 8324,
  diaSemana: 'possible generously where',
  esHorarioAtencion: true,
  estado: 'split',
  desdeHoraAlmuerzo: dayjs('2024-11-05T09:41'),
  hastaHoraAlmuerzo: dayjs('2024-11-05T20:58'),
};

export const sampleWithNewData: NewHorarioConsulta = {
  fechaHorario: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T19:19'),
  duracionMinutos: 31815,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
