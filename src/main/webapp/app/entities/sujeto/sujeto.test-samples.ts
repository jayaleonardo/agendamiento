import dayjs from 'dayjs/esm';

import { ISujeto, NewSujeto } from './sujeto.model';

export const sampleWithRequiredData: ISujeto = {
  id: 31946,
  nombre: 'incidentally twin',
  apellido: 'antagonize adventurously failing',
  documentoIdentidad: 'indeed sarong',
};

export const sampleWithPartialData: ISujeto = {
  id: 2849,
  nombre: 'delectable descent whup',
  apellido: 'gah less',
  documentoIdentidad: 'fax concerning vulgarise',
  fechaNacimiento: dayjs('2024-11-05'),
};

export const sampleWithFullData: ISujeto = {
  id: 29476,
  nombre: 'repurpose',
  segundoNombre: 'worth but',
  apellido: 'permafrost widow',
  segundoApellido: 'er everlasting plan',
  documentoIdentidad: 'pfft baa during',
  estado: 'atop',
  sexo: 'whoever refine lovingly',
  fechaNacimiento: dayjs('2024-11-05'),
};

export const sampleWithNewData: NewSujeto = {
  nombre: 'safe canter',
  apellido: 'cheetah',
  documentoIdentidad: 'pomelo times ouch',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
