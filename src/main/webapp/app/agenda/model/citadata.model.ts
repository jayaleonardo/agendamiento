import dayjs from 'dayjs/esm';

export interface ICitaData {
  id: number;
  fecha?: dayjs.Dayjs | null;
  horainicio?: string | null;
  horarioFin?: string | null;
  duracion?: number | null;
  consultorio?: string | null;
  paciente?: string | null;
  estado?: string | null;
}
