import dayjs from 'dayjs/esm';

import { ICita, NewCita } from './cita.model';

export const sampleWithRequiredData: ICita = {
  id: 12353,
  fechaCita: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T07:11'),
  duracionMinutos: 536,
};

export const sampleWithPartialData: ICita = {
  id: 28839,
  fechaCita: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T02:29'),
  duracionMinutos: 30671,
  tipoVisita: 'brightly',
  canalAtencion: 'unabashedly above extent',
  recordatorio: 'er nauseate permafrost',
  motivoConsulta: 'yuck whoa ecstatic',
  informacionReserva: 'per that at',
};

export const sampleWithFullData: ICita = {
  id: 5880,
  fechaCita: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T06:15'),
  duracionMinutos: 8042,
  estado: 'yearningly stitcher',
  tipoVisita: 'pocket-watch gee oh',
  canalAtencion: 'along disrespect now',
  observacion: 'adjudge dereference suddenly',
  recordatorio: 'opposite',
  motivoConsulta: 'sure-footed pepper gullible',
  detalleConsultaVirtual: 'terribly',
  virtual: true,
  informacionReserva: 'ugh',
  tarea: 'insignificant',
};

export const sampleWithNewData: NewCita = {
  fechaCita: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T16:44'),
  duracionMinutos: 5397,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
