import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IDireccion, NewDireccion } from '../direccion.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDireccion for edit and NewDireccionFormGroupInput for create.
 */
type DireccionFormGroupInput = IDireccion | PartialWithRequiredKeyOf<NewDireccion>;

type DireccionFormDefaults = Pick<NewDireccion, 'id'>;

type DireccionFormGroupContent = {
  id: FormControl<IDireccion['id'] | NewDireccion['id']>;
  idDireccion: FormControl<IDireccion['idDireccion']>;
  pais: FormControl<IDireccion['pais']>;
  provincia: FormControl<IDireccion['provincia']>;
  ciudad: FormControl<IDireccion['ciudad']>;
  calles: FormControl<IDireccion['calles']>;
  nroDomicilio: FormControl<IDireccion['nroDomicilio']>;
  sujeto: FormControl<IDireccion['sujeto']>;
};

export type DireccionFormGroup = FormGroup<DireccionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DireccionFormService {
  createDireccionFormGroup(direccion: DireccionFormGroupInput = { id: null }): DireccionFormGroup {
    const direccionRawValue = {
      ...this.getFormDefaults(),
      ...direccion,
    };
    return new FormGroup<DireccionFormGroupContent>({
      id: new FormControl(
        { value: direccionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      idDireccion: new FormControl(direccionRawValue.idDireccion, {
        validators: [Validators.required],
      }),
      pais: new FormControl(direccionRawValue.pais, {
        validators: [Validators.required],
      }),
      provincia: new FormControl(direccionRawValue.provincia, {
        validators: [Validators.required],
      }),
      ciudad: new FormControl(direccionRawValue.ciudad, {
        validators: [Validators.required],
      }),
      calles: new FormControl(direccionRawValue.calles, {
        validators: [Validators.required],
      }),
      nroDomicilio: new FormControl(direccionRawValue.nroDomicilio),
      sujeto: new FormControl(direccionRawValue.sujeto),
    });
  }

  getDireccion(form: DireccionFormGroup): IDireccion | NewDireccion {
    return form.getRawValue() as IDireccion | NewDireccion;
  }

  resetForm(form: DireccionFormGroup, direccion: DireccionFormGroupInput): void {
    const direccionRawValue = { ...this.getFormDefaults(), ...direccion };
    form.reset(
      {
        ...direccionRawValue,
        id: { value: direccionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DireccionFormDefaults {
    return {
      id: null,
    };
  }
}
