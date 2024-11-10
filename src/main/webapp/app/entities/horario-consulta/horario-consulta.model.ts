import dayjs from 'dayjs/esm';
import { IEspecialista } from 'app/entities/especialista/especialista.model';

export interface IHorarioConsulta {
  id: number;
  desde?: dayjs.Dayjs | null;
  hasta?: dayjs.Dayjs | null;
  horaInicio?: dayjs.Dayjs | null;
  horaFin?: dayjs.Dayjs | null;
  duracionMinutos?: number | null;
  diaSemana?: string | null;
  esHorarioAtencion?: boolean | null;
  estado?: string | null;
  desdeHoraAlmuerzo?: dayjs.Dayjs | null;
  hastaHoraAlmuerzo?: dayjs.Dayjs | null;
  especialista?: Pick<IEspecialista, 'id'> | null;
}

export type NewHorarioConsulta = Omit<IHorarioConsulta, 'id'> & { id: null };
