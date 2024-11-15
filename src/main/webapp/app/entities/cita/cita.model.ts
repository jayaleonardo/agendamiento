import dayjs from 'dayjs/esm';
import { IEspecialista } from 'app/entities/especialista/especialista.model';
import { ITipoTerapia } from 'app/entities/tipo-terapia/tipo-terapia.model';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { IProgramacion } from 'app/entities/programacion/programacion.model';

export interface ICita {
  id: number;
  fechaCita?: dayjs.Dayjs | null;
  horaInicio?: dayjs.Dayjs | null;
  duracionMinutos?: number | null;
  estado?: string | null;
  tipoVisita?: string | null;
  canalAtencion?: string | null;
  observacion?: string | null;
  recordatorio?: string | null;
  motivoConsulta?: string | null;
  detalleConsultaVirtual?: string | null;
  virtual?: boolean | null;
  informacionReserva?: string | null;
  especialista?: Pick<IEspecialista, 'id'> | null;
  tipoTerapia?: Pick<ITipoTerapia, 'id'> | null;
  paciente?: Pick<IPaciente, 'id'> | null;
  programacion?: Pick<IProgramacion, 'id'> | null;
}

export type NewCita = Omit<ICita, 'id'> & { id: null };
