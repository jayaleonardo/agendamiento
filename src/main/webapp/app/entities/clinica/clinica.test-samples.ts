import { IClinica, NewClinica } from './clinica.model';

export const sampleWithRequiredData: IClinica = {
  id: 14902,
  nombre: 'rudely schedule',
  telefono: 'brightly before emergent',
  correo: 'vacantly lest yearningly',
  horarioAtencion: 'axe',
};

export const sampleWithPartialData: IClinica = {
  id: 4168,
  nombre: 'atop',
  telefono: 'remarkable',
  correo: 'newsletter',
  horarioAtencion: 'resolve',
};

export const sampleWithFullData: IClinica = {
  id: 3195,
  nombre: 'meatloaf furthermore coolly',
  telefono: 'train phooey',
  correo: 'scoop',
  horarioAtencion: 'apropos',
};

export const sampleWithNewData: NewClinica = {
  nombre: 'overcoat which outside',
  telefono: 'swill forenenst',
  correo: 'given shrill task',
  horarioAtencion: 'gee',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
