import { ISujeto } from 'app/entities/sujeto/sujeto.model';

export interface IContacto {
  id: number;
  telefono?: string | null;
  correo?: string | null;
  codigoPais?: string | null;
  celular?: string | null;
  sujeto?: Pick<ISujeto, 'id'> | null;
}

export type NewContacto = Omit<IContacto, 'id'> & { id: null };
