import { IDireccion, NewDireccion } from './direccion.model';

export const sampleWithRequiredData: IDireccion = {
  id: 29054,
  idDireccion: 29757,
  pais: 'ew',
  provincia: 'gadzooks integer why',
  ciudad: 'toward save overconfidently',
  calles: 'rusty',
};

export const sampleWithPartialData: IDireccion = {
  id: 26652,
  idDireccion: 27180,
  pais: 'inside nauseate excluding',
  provincia: 'collaboration gosh emphasise',
  ciudad: 'finally confute reassuringly',
  calles: 'now',
};

export const sampleWithFullData: IDireccion = {
  id: 7316,
  idDireccion: 13314,
  pais: 'brilliant',
  provincia: 'till who inside',
  ciudad: 'stealthily obnoxiously redraw',
  calles: 'queasily',
  nroDomicilio: 'excluding butter',
};

export const sampleWithNewData: NewDireccion = {
  idDireccion: 8522,
  pais: 'gently blah adumbrate',
  provincia: 'oof',
  ciudad: 'inquisitively',
  calles: 'brood bin abaft',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
