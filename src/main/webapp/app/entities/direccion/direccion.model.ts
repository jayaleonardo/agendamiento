import { ISujeto } from 'app/entities/sujeto/sujeto.model';

export interface IDireccion {
  id: number;
  idDireccion?: number | null;
  pais?: string | null;
  provincia?: string | null;
  ciudad?: string | null;
  calles?: string | null;
  nroDomicilio?: string | null;
  sujeto?: Pick<ISujeto, 'id'> | null;
}

export type NewDireccion = Omit<IDireccion, 'id'> & { id: null };
