import { IContacto, NewContacto } from './contacto.model';

export const sampleWithRequiredData: IContacto = {
  id: 2190,
  correo: 'failing what word',
  codigoPais: 'brr including odd',
};

export const sampleWithPartialData: IContacto = {
  id: 514,
  correo: 'valiantly upon down',
  codigoPais: 'unless absentmindedly',
  celular: 'elastic hopelessly',
};

export const sampleWithFullData: IContacto = {
  id: 14663,
  telefono: 'rightfully tame although',
  correo: 'endow zowie',
  codigoPais: 'membership',
  celular: 'vacantly',
};

export const sampleWithNewData: NewContacto = {
  correo: 'about',
  codigoPais: 'oof',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
