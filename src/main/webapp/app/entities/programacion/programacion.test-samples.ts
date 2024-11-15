import dayjs from 'dayjs/esm';

import { IProgramacion, NewProgramacion } from './programacion.model';

export const sampleWithRequiredData: IProgramacion = {
  id: 6058,
  fecha: dayjs('2024-11-05'),
  desde: dayjs('2024-11-05T11:26'),
  hasta: dayjs('2024-11-05T21:04'),
};

export const sampleWithPartialData: IProgramacion = {
  id: 31481,
  fecha: dayjs('2024-11-05'),
  desde: dayjs('2024-11-05T19:15'),
  hasta: dayjs('2024-11-05T21:50'),
};

export const sampleWithFullData: IProgramacion = {
  id: 566,
  fecha: dayjs('2024-11-05'),
  desde: dayjs('2024-11-04T23:57'),
  hasta: dayjs('2024-11-05T14:48'),
};

export const sampleWithNewData: NewProgramacion = {
  fecha: dayjs('2024-11-05'),
  desde: dayjs('2024-11-05T01:04'),
  hasta: dayjs('2024-11-04T23:50'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
