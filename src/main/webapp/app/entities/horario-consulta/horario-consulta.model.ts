import dayjs from 'dayjs/esm';
import { IEspecialista } from 'app/entities/especialista/especialista.model';

export interface IHorarioConsulta {
  id: number;
  fechaHorario?: dayjs.Dayjs | null;
  horaInicio?: dayjs.Dayjs | null;
  duracionMinutos?: number | null;
  diaSemana?: string | null;
  esHorarioAtencion?: boolean | null;
  estado?: string | null;
  especialista?: Pick<IEspecialista, 'id'> | null;
}

export type NewHorarioConsulta = Omit<IHorarioConsulta, 'id'> & { id: null };
