export interface ITipoTerapia {
  id: number;
  descripcion?: string | null;
}

export type NewTipoTerapia = Omit<ITipoTerapia, 'id'> & { id: null };
