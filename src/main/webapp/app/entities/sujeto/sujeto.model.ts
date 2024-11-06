import dayjs from 'dayjs/esm';

export interface ISujeto {
  id: number;
  nombre?: string | null;
  segundoNombre?: string | null;
  apellido?: string | null;
  segundoApellido?: string | null;
  documentoIdentidad?: string | null;
  estado?: string | null;
  sexo?: string | null;
  fechaNacimiento?: dayjs.Dayjs | null;
}

export type NewSujeto = Omit<ISujeto, 'id'> & { id: null };
