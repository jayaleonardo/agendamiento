import dayjs from 'dayjs/esm';

import { IProgramacion, NewProgramacion } from './programacion.model';

export const sampleWithRequiredData: IProgramacion = {
  id: 6058,
  fechaDesde: dayjs('2024-11-05'),
  fechaHasta: dayjs('2024-11-05'),
  duracionMinutos: 29096,
};

export const sampleWithPartialData: IProgramacion = {
  id: 30135,
  fechaDesde: dayjs('2024-11-05'),
  fechaHasta: dayjs('2024-11-05'),
  duracionMinutos: 267,
  hastaHoraAlmuerzo: dayjs('2024-11-05T14:48'),
};

export const sampleWithFullData: IProgramacion = {
  id: 2292,
  fechaDesde: dayjs('2024-11-05'),
  fechaHasta: dayjs('2024-11-04'),
  duracionMinutos: 5846,
  desdeHoraAlmuerzo: dayjs('2024-11-05T04:58'),
  hastaHoraAlmuerzo: dayjs('2024-11-05T15:07'),
  diasSemana: 'approximate',
};

export const sampleWithNewData: NewProgramacion = {
  fechaDesde: dayjs('2024-11-05'),
  fechaHasta: dayjs('2024-11-05'),
  duracionMinutos: 31743,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
