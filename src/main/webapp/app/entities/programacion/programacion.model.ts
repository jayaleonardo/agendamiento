import dayjs from 'dayjs/esm';
import { IHorarioConsulta } from 'app/entities/horario-consulta/horario-consulta.model';

export interface IProgramacion {
  id: number;
  fechaDesde?: dayjs.Dayjs | null;
  fechaHasta?: dayjs.Dayjs | null;
  duracionMinutos?: number | null;
  desdeHoraAlmuerzo?: dayjs.Dayjs | null;
  hastaHoraAlmuerzo?: dayjs.Dayjs | null;
  diasSemana?: string | null;
  horarioConsulta?: Pick<IHorarioConsulta, 'id'> | null;
}

export type NewProgramacion = Omit<IProgramacion, 'id'> & { id: null };
