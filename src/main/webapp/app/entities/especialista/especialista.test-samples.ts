import { IEspecialista, NewEspecialista } from './especialista.model';

export const sampleWithRequiredData: IEspecialista = {
  id: 26066,
  especialidad: 'gah',
  codigoDoctor: 'wearily nor halt',
  nroConsultorio: 'pfft how',
};

export const sampleWithPartialData: IEspecialista = {
  id: 25547,
  especialidad: 'unto psst meanwhile',
  codigoDoctor: 'burly after',
  nroConsultorio: 'wisely polite',
};

export const sampleWithFullData: IEspecialista = {
  id: 1369,
  especialidad: 'marketplace upon',
  codigoDoctor: 'foolishly micromanage',
  nroConsultorio: 'denitrify never but',
  fotoUrl: 'hoof versus never',
};

export const sampleWithNewData: NewEspecialista = {
  especialidad: 'kiddingly louse once',
  codigoDoctor: 'meh',
  nroConsultorio: 'bruised mountain geez',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
