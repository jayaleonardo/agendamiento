import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IClinica, NewClinica } from '../clinica.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IClinica for edit and NewClinicaFormGroupInput for create.
 */
type ClinicaFormGroupInput = IClinica | PartialWithRequiredKeyOf<NewClinica>;

type ClinicaFormDefaults = Pick<NewClinica, 'id'>;

type ClinicaFormGroupContent = {
  id: FormControl<IClinica['id'] | NewClinica['id']>;
  nombre: FormControl<IClinica['nombre']>;
  telefono: FormControl<IClinica['telefono']>;
  correo: FormControl<IClinica['correo']>;
  horarioAtencion: FormControl<IClinica['horarioAtencion']>;
};

export type ClinicaFormGroup = FormGroup<ClinicaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ClinicaFormService {
  createClinicaFormGroup(clinica: ClinicaFormGroupInput = { id: null }): ClinicaFormGroup {
    const clinicaRawValue = {
      ...this.getFormDefaults(),
      ...clinica,
    };
    return new FormGroup<ClinicaFormGroupContent>({
      id: new FormControl(
        { value: clinicaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombre: new FormControl(clinicaRawValue.nombre, {
        validators: [Validators.required],
      }),
      telefono: new FormControl(clinicaRawValue.telefono, {
        validators: [Validators.required],
      }),
      correo: new FormControl(clinicaRawValue.correo, {
        validators: [Validators.required],
      }),
      horarioAtencion: new FormControl(clinicaRawValue.horarioAtencion, {
        validators: [Validators.required],
      }),
    });
  }

  getClinica(form: ClinicaFormGroup): IClinica | NewClinica {
    return form.getRawValue() as IClinica | NewClinica;
  }

  resetForm(form: ClinicaFormGroup, clinica: ClinicaFormGroupInput): void {
    const clinicaRawValue = { ...this.getFormDefaults(), ...clinica };
    form.reset(
      {
        ...clinicaRawValue,
        id: { value: clinicaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ClinicaFormDefaults {
    return {
      id: null,
    };
  }
}
