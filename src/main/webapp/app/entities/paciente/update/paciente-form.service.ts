import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IPaciente, NewPaciente } from '../paciente.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPaciente for edit and NewPacienteFormGroupInput for create.
 */
type PacienteFormGroupInput = IPaciente | PartialWithRequiredKeyOf<NewPaciente>;

type PacienteFormDefaults = Pick<NewPaciente, 'id'>;

type PacienteFormGroupContent = {
  id: FormControl<IPaciente['id'] | NewPaciente['id']>;
  nroHistoria: FormControl<IPaciente['nroHistoria']>;
  nombreRepresentante: FormControl<IPaciente['nombreRepresentante']>;
  parentescoRepresentante: FormControl<IPaciente['parentescoRepresentante']>;
  correoRepresentante: FormControl<IPaciente['correoRepresentante']>;
  celularRepresentante: FormControl<IPaciente['celularRepresentante']>;
  direccionRepresentante: FormControl<IPaciente['direccionRepresentante']>;
  sujeto: FormControl<IPaciente['sujeto']>;
};

export type PacienteFormGroup = FormGroup<PacienteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PacienteFormService {
  createPacienteFormGroup(paciente: PacienteFormGroupInput = { id: null }): PacienteFormGroup {
    const pacienteRawValue = {
      ...this.getFormDefaults(),
      ...paciente,
    };
    return new FormGroup<PacienteFormGroupContent>({
      id: new FormControl(
        { value: pacienteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nroHistoria: new FormControl(pacienteRawValue.nroHistoria, {
        validators: [Validators.required],
      }),
      nombreRepresentante: new FormControl(pacienteRawValue.nombreRepresentante),
      parentescoRepresentante: new FormControl(pacienteRawValue.parentescoRepresentante),
      correoRepresentante: new FormControl(pacienteRawValue.correoRepresentante),
      celularRepresentante: new FormControl(pacienteRawValue.celularRepresentante),
      direccionRepresentante: new FormControl(pacienteRawValue.direccionRepresentante),
      sujeto: new FormControl(pacienteRawValue.sujeto),
    });
  }

  getPaciente(form: PacienteFormGroup): IPaciente | NewPaciente {
    return form.getRawValue() as IPaciente | NewPaciente;
  }

  resetForm(form: PacienteFormGroup, paciente: PacienteFormGroupInput): void {
    const pacienteRawValue = { ...this.getFormDefaults(), ...paciente };
    form.reset(
      {
        ...pacienteRawValue,
        id: { value: pacienteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PacienteFormDefaults {
    return {
      id: null,
    };
  }
}
