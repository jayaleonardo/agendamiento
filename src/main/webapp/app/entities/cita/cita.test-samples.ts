import dayjs from 'dayjs/esm';

import { ICita, NewCita } from './cita.model';

export const sampleWithRequiredData: ICita = {
  id: 15084,
  fechaCita: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T09:10'),
  duracionMinutos: 10132,
};

export const sampleWithPartialData: ICita = {
  id: 923,
  fechaCita: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T20:53'),
  duracionMinutos: 29029,
  estado: 'reproachfully',
  canalAtencion: 'hence',
  observacion: 'and',
  motivoConsulta: 'extent',
  detalleConsultaVirtual: 'er nauseate permafrost',
};

export const sampleWithFullData: ICita = {
  id: 30236,
  fechaCita: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T16:00'),
  duracionMinutos: 27947,
  estado: 'mosh',
  tipoVisita: 'on ack',
  canalAtencion: 'prejudge since down',
  observacion: 'fiddle',
  recordatorio: 'musty',
  motivoConsulta: 'beside cavernous hearten',
  detalleConsultaVirtual: 'disinherit gadzooks',
  virtual: false,
  informacionReserva: 'slowly',
};

export const sampleWithNewData: NewCita = {
  fechaCita: dayjs('2024-11-05'),
  horaInicio: dayjs('2024-11-05T08:10'),
  duracionMinutos: 18569,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
