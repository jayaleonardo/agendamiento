import dayjs from 'dayjs/esm';

import { IProgramacion, NewProgramacion } from './programacion.model';

export const sampleWithRequiredData: IProgramacion = {
  id: 17689,
  fecha: dayjs('2024-11-05'),
  desde: dayjs('2024-11-05T13:20'),
  hasta: dayjs('2024-11-05T04:12'),
};

export const sampleWithPartialData: IProgramacion = {
  id: 13463,
  fecha: dayjs('2024-11-05'),
  desde: dayjs('2024-11-05T21:04'),
  hasta: dayjs('2024-11-05T22:49'),
};

export const sampleWithFullData: IProgramacion = {
  id: 14464,
  fecha: dayjs('2024-11-05'),
  desde: dayjs('2024-11-05T21:50'),
  hasta: dayjs('2024-11-05T00:10'),
};

export const sampleWithNewData: NewProgramacion = {
  fecha: dayjs('2024-11-05'),
  desde: dayjs('2024-11-04T23:57'),
  hasta: dayjs('2024-11-05T14:48'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
