export interface IClinica {
  id: number;
  nombre?: string | null;
  telefono?: string | null;
  correo?: string | null;
  horarioAtencion?: string | null;
}

export type NewClinica = Omit<IClinica, 'id'> & { id: null };
