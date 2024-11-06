import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ITipoTerapia, NewTipoTerapia } from '../tipo-terapia.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITipoTerapia for edit and NewTipoTerapiaFormGroupInput for create.
 */
type TipoTerapiaFormGroupInput = ITipoTerapia | PartialWithRequiredKeyOf<NewTipoTerapia>;

type TipoTerapiaFormDefaults = Pick<NewTipoTerapia, 'id'>;

type TipoTerapiaFormGroupContent = {
  id: FormControl<ITipoTerapia['id'] | NewTipoTerapia['id']>;
  descripcion: FormControl<ITipoTerapia['descripcion']>;
};

export type TipoTerapiaFormGroup = FormGroup<TipoTerapiaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TipoTerapiaFormService {
  createTipoTerapiaFormGroup(tipoTerapia: TipoTerapiaFormGroupInput = { id: null }): TipoTerapiaFormGroup {
    const tipoTerapiaRawValue = {
      ...this.getFormDefaults(),
      ...tipoTerapia,
    };
    return new FormGroup<TipoTerapiaFormGroupContent>({
      id: new FormControl(
        { value: tipoTerapiaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      descripcion: new FormControl(tipoTerapiaRawValue.descripcion, {
        validators: [Validators.required],
      }),
    });
  }

  getTipoTerapia(form: TipoTerapiaFormGroup): ITipoTerapia | NewTipoTerapia {
    return form.getRawValue() as ITipoTerapia | NewTipoTerapia;
  }

  resetForm(form: TipoTerapiaFormGroup, tipoTerapia: TipoTerapiaFormGroupInput): void {
    const tipoTerapiaRawValue = { ...this.getFormDefaults(), ...tipoTerapia };
    form.reset(
      {
        ...tipoTerapiaRawValue,
        id: { value: tipoTerapiaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TipoTerapiaFormDefaults {
    return {
      id: null,
    };
  }
}
