import { ITipoTerapia, NewTipoTerapia } from './tipo-terapia.model';

export const sampleWithRequiredData: ITipoTerapia = {
  id: 16598,
  descripcion: 'vacantly explode but',
};

export const sampleWithPartialData: ITipoTerapia = {
  id: 10359,
  descripcion: 'whenever angrily',
};

export const sampleWithFullData: ITipoTerapia = {
  id: 21026,
  descripcion: 'ouch',
};

export const sampleWithNewData: NewTipoTerapia = {
  descripcion: 'slap',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
