import { ISujeto } from 'app/entities/sujeto/sujeto.model';

export interface IEspecialista {
  id: number;
  especialidad?: string | null;
  codigoDoctor?: string | null;
  nroConsultorio?: string | null;
  fotoUrl?: string | null;
  sujeto?: ISujeto | null;
}

export type NewEspecialista = Omit<IEspecialista, 'id'> & { id: null };
