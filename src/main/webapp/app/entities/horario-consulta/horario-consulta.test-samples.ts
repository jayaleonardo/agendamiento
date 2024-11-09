import dayjs from 'dayjs/esm';

import { IHorarioConsulta, NewHorarioConsulta } from './horario-consulta.model';

export const sampleWithRequiredData: IHorarioConsulta = {
  id: 32527,
  desde: dayjs('2024-11-05'),
  hasta: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T13:12'),
  duracionMinutos: 31321,
};

export const sampleWithPartialData: IHorarioConsulta = {
  id: 4768,
  desde: dayjs('2024-11-05'),
  hasta: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T14:45'),
  duracionMinutos: 3750,
  esHorarioAtencion: true,
  estado: 'unwelcome',
};

export const sampleWithFullData: IHorarioConsulta = {
  id: 18606,
  desde: dayjs('2024-11-05'),
  hasta: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T01:36'),
  duracionMinutos: 16870,
  diaSemana: 'uh-huh',
  esHorarioAtencion: true,
  estado: 'yuck',
  desdeHoraAlmuerzo: dayjs('2024-11-05T17:08'),
  hastaHoraAlmuerzo: dayjs('2024-11-05T16:11'),
};

export const sampleWithNewData: NewHorarioConsulta = {
  desde: dayjs('2024-11-05'),
  hasta: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T20:58'),
  duracionMinutos: 1764,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
