import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IContacto, NewContacto } from '../contacto.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContacto for edit and NewContactoFormGroupInput for create.
 */
type ContactoFormGroupInput = IContacto | PartialWithRequiredKeyOf<NewContacto>;

type ContactoFormDefaults = Pick<NewContacto, 'id'>;

type ContactoFormGroupContent = {
  id: FormControl<IContacto['id'] | NewContacto['id']>;
  telefono: FormControl<IContacto['telefono']>;
  correo: FormControl<IContacto['correo']>;
  codigoPais: FormControl<IContacto['codigoPais']>;
  celular: FormControl<IContacto['celular']>;
  sujeto: FormControl<IContacto['sujeto']>;
};

export type ContactoFormGroup = FormGroup<ContactoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContactoFormService {
  createContactoFormGroup(contacto: ContactoFormGroupInput = { id: null }): ContactoFormGroup {
    const contactoRawValue = {
      ...this.getFormDefaults(),
      ...contacto,
    };
    return new FormGroup<ContactoFormGroupContent>({
      id: new FormControl(
        { value: contactoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      telefono: new FormControl(contactoRawValue.telefono),
      correo: new FormControl(contactoRawValue.correo, {
        validators: [Validators.required],
      }),
      codigoPais: new FormControl(contactoRawValue.codigoPais, {
        validators: [Validators.required],
      }),
      celular: new FormControl(contactoRawValue.celular),
      sujeto: new FormControl(contactoRawValue.sujeto),
    });
  }

  getContacto(form: ContactoFormGroup): IContacto | NewContacto {
    return form.getRawValue() as IContacto | NewContacto;
  }

  resetForm(form: ContactoFormGroup, contacto: ContactoFormGroupInput): void {
    const contactoRawValue = { ...this.getFormDefaults(), ...contacto };
    form.reset(
      {
        ...contactoRawValue,
        id: { value: contactoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ContactoFormDefaults {
    return {
      id: null,
    };
  }
}
