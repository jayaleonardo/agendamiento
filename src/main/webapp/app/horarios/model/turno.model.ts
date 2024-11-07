import dayjs from 'dayjs/esm';

export interface ITurnoEspecialidad {
  programacionid: number;
  fecha?: Date | null;
  desde?: dayjs.Dayjs | null;
  hasta?: dayjs.Dayjs | null;
  especialidad?: string | null;
  nroconsultorio?: string | null;
  especialista?: string | null;
}
