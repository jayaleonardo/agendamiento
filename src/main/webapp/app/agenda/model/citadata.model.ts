import dayjs from 'dayjs/esm';

export interface ICitaData {
  id: number;
  fecha?: dayjs.Dayjs | null;
  horainicio?: string | null;
  horafin?: string | null;
  duracion?: number | null;
  consultorio?: string | null;
  paciente?: string | null;
  estado?: string | null;
}
