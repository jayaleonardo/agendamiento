import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IEspecialista, NewEspecialista } from '../especialista.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEspecialista for edit and NewEspecialistaFormGroupInput for create.
 */
type EspecialistaFormGroupInput = IEspecialista | PartialWithRequiredKeyOf<NewEspecialista>;

type EspecialistaFormDefaults = Pick<NewEspecialista, 'id'>;

type EspecialistaFormGroupContent = {
  id: FormControl<IEspecialista['id'] | NewEspecialista['id']>;
  especialidad: FormControl<IEspecialista['especialidad']>;
  codigoDoctor: FormControl<IEspecialista['codigoDoctor']>;
  nroConsultorio: FormControl<IEspecialista['nroConsultorio']>;
  fotoUrl: FormControl<IEspecialista['fotoUrl']>;
  sujeto: FormControl<IEspecialista['sujeto']>;
};

export type EspecialistaFormGroup = FormGroup<EspecialistaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EspecialistaFormService {
  createEspecialistaFormGroup(especialista: EspecialistaFormGroupInput = { id: null }): EspecialistaFormGroup {
    const especialistaRawValue = {
      ...this.getFormDefaults(),
      ...especialista,
    };
    return new FormGroup<EspecialistaFormGroupContent>({
      id: new FormControl(
        { value: especialistaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      especialidad: new FormControl(especialistaRawValue.especialidad, {
        validators: [Validators.required],
      }),
      codigoDoctor: new FormControl(especialistaRawValue.codigoDoctor, {
        validators: [Validators.required],
      }),
      nroConsultorio: new FormControl(especialistaRawValue.nroConsultorio, {
        validators: [Validators.required],
      }),
      fotoUrl: new FormControl(especialistaRawValue.fotoUrl),
      sujeto: new FormControl(especialistaRawValue.sujeto),
    });
  }

  getEspecialista(form: EspecialistaFormGroup): IEspecialista | NewEspecialista {
    return form.getRawValue() as IEspecialista | NewEspecialista;
  }

  resetForm(form: EspecialistaFormGroup, especialista: EspecialistaFormGroupInput): void {
    const especialistaRawValue = { ...this.getFormDefaults(), ...especialista };
    form.reset(
      {
        ...especialistaRawValue,
        id: { value: especialistaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EspecialistaFormDefaults {
    return {
      id: null,
    };
  }
}
