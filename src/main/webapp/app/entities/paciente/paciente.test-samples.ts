import { IPaciente, NewPaciente } from './paciente.model';

export const sampleWithRequiredData: IPaciente = {
  id: 14950,
  nroHistoria: 3983,
};

export const sampleWithPartialData: IPaciente = {
  id: 12431,
  nroHistoria: 14638,
  nombreRepresentante: 'ew fedora ah',
  correoRepresentante: 'blindly twist',
  direccionRepresentante: 'beyond solidly',
};

export const sampleWithFullData: IPaciente = {
  id: 13874,
  nroHistoria: 22280,
  nombreRepresentante: 'oof cruelly though',
  parentescoRepresentante: 'snuggle',
  correoRepresentante: 'until ah',
  celularRepresentante: 'with gah now',
  direccionRepresentante: 'softly hastily vastly',
};

export const sampleWithNewData: NewPaciente = {
  nroHistoria: 24866,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
