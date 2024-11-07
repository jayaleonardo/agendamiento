import dayjs from 'dayjs/esm';
import { IHorarioConsulta } from 'app/entities/horario-consulta/horario-consulta.model';

export interface IProgramacion {
  id: number;
  fecha?: dayjs.Dayjs | null;
  desde?: dayjs.Dayjs | null;
  hasta?: dayjs.Dayjs | null;
  horarioConsulta?: Pick<IHorarioConsulta, 'id'> | null;
}

export type NewProgramacion = Omit<IProgramacion, 'id'> & { id: null };
