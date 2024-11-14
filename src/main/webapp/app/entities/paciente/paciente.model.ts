import { ISujeto } from 'app/entities/sujeto/sujeto.model';

export interface IPaciente {
  id: number;
  nroHistoria?: number | null;
  nombreRepresentante?: string | null;
  parentescoRepresentante?: string | null;
  correoRepresentante?: string | null;
  celularRepresentante?: string | null;
  direccionRepresentante?: string | null;
  sujeto?: ISujeto | null;
}

export type NewPaciente = Omit<IPaciente, 'id'> & { id: null };
