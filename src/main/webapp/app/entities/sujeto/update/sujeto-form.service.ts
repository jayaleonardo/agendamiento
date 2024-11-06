import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ISujeto, NewSujeto } from '../sujeto.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISujeto for edit and NewSujetoFormGroupInput for create.
 */
type SujetoFormGroupInput = ISujeto | PartialWithRequiredKeyOf<NewSujeto>;

type SujetoFormDefaults = Pick<NewSujeto, 'id'>;

type SujetoFormGroupContent = {
  id: FormControl<ISujeto['id'] | NewSujeto['id']>;
  nombre: FormControl<ISujeto['nombre']>;
  segundoNombre: FormControl<ISujeto['segundoNombre']>;
  apellido: FormControl<ISujeto['apellido']>;
  segundoApellido: FormControl<ISujeto['segundoApellido']>;
  documentoIdentidad: FormControl<ISujeto['documentoIdentidad']>;
  estado: FormControl<ISujeto['estado']>;
  sexo: FormControl<ISujeto['sexo']>;
  fechaNacimiento: FormControl<ISujeto['fechaNacimiento']>;
};

export type SujetoFormGroup = FormGroup<SujetoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SujetoFormService {
  createSujetoFormGroup(sujeto: SujetoFormGroupInput = { id: null }): SujetoFormGroup {
    const sujetoRawValue = {
      ...this.getFormDefaults(),
      ...sujeto,
    };
    return new FormGroup<SujetoFormGroupContent>({
      id: new FormControl(
        { value: sujetoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombre: new FormControl(sujetoRawValue.nombre, {
        validators: [Validators.required],
      }),
      segundoNombre: new FormControl(sujetoRawValue.segundoNombre),
      apellido: new FormControl(sujetoRawValue.apellido, {
        validators: [Validators.required],
      }),
      segundoApellido: new FormControl(sujetoRawValue.segundoApellido),
      documentoIdentidad: new FormControl(sujetoRawValue.documentoIdentidad, {
        validators: [Validators.required],
      }),
      estado: new FormControl(sujetoRawValue.estado),
      sexo: new FormControl(sujetoRawValue.sexo),
      fechaNacimiento: new FormControl(sujetoRawValue.fechaNacimiento),
    });
  }

  getSujeto(form: SujetoFormGroup): ISujeto | NewSujeto {
    return form.getRawValue() as ISujeto | NewSujeto;
  }

  resetForm(form: SujetoFormGroup, sujeto: SujetoFormGroupInput): void {
    const sujetoRawValue = { ...this.getFormDefaults(), ...sujeto };
    form.reset(
      {
        ...sujetoRawValue,
        id: { value: sujetoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SujetoFormDefaults {
    return {
      id: null,
    };
  }
}
