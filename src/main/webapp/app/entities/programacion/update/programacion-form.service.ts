import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProgramacion, NewProgramacion } from '../programacion.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProgramacion for edit and NewProgramacionFormGroupInput for create.
 */
type ProgramacionFormGroupInput = IProgramacion | PartialWithRequiredKeyOf<NewProgramacion>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProgramacion | NewProgramacion> = Omit<T, 'desde' | 'hasta'> & {
  desde?: string | null;
  hasta?: string | null;
};

type ProgramacionFormRawValue = FormValueOf<IProgramacion>;

type NewProgramacionFormRawValue = FormValueOf<NewProgramacion>;

type ProgramacionFormDefaults = Pick<NewProgramacion, 'id' | 'desde' | 'hasta'>;

type ProgramacionFormGroupContent = {
  id: FormControl<ProgramacionFormRawValue['id'] | NewProgramacion['id']>;
  fecha: FormControl<ProgramacionFormRawValue['fecha']>;
  desde: FormControl<ProgramacionFormRawValue['desde']>;
  hasta: FormControl<ProgramacionFormRawValue['hasta']>;
  horarioConsulta: FormControl<ProgramacionFormRawValue['horarioConsulta']>;
};

export type ProgramacionFormGroup = FormGroup<ProgramacionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProgramacionFormService {
  createProgramacionFormGroup(programacion: ProgramacionFormGroupInput = { id: null }): ProgramacionFormGroup {
    const programacionRawValue = this.convertProgramacionToProgramacionRawValue({
      ...this.getFormDefaults(),
      ...programacion,
    });
    return new FormGroup<ProgramacionFormGroupContent>({
      id: new FormControl(
        { value: programacionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fecha: new FormControl(programacionRawValue.fecha, {
        validators: [Validators.required],
      }),
      desde: new FormControl(programacionRawValue.desde, {
        validators: [Validators.required],
      }),
      hasta: new FormControl(programacionRawValue.hasta, {
        validators: [Validators.required],
      }),
      horarioConsulta: new FormControl(programacionRawValue.horarioConsulta),
    });
  }

  getProgramacion(form: ProgramacionFormGroup): IProgramacion | NewProgramacion {
    return this.convertProgramacionRawValueToProgramacion(form.getRawValue() as ProgramacionFormRawValue | NewProgramacionFormRawValue);
  }

  resetForm(form: ProgramacionFormGroup, programacion: ProgramacionFormGroupInput): void {
    const programacionRawValue = this.convertProgramacionToProgramacionRawValue({ ...this.getFormDefaults(), ...programacion });
    form.reset(
      {
        ...programacionRawValue,
        id: { value: programacionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ProgramacionFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      desde: currentTime,
      hasta: currentTime,
    };
  }

  private convertProgramacionRawValueToProgramacion(
    rawProgramacion: ProgramacionFormRawValue | NewProgramacionFormRawValue,
  ): IProgramacion | NewProgramacion {
    return {
      ...rawProgramacion,
      desde: dayjs(rawProgramacion.desde, DATE_TIME_FORMAT),
      hasta: dayjs(rawProgramacion.hasta, DATE_TIME_FORMAT),
    };
  }

  private convertProgramacionToProgramacionRawValue(
    programacion: IProgramacion | (Partial<NewProgramacion> & ProgramacionFormDefaults),
  ): ProgramacionFormRawValue | PartialWithRequiredKeyOf<NewProgramacionFormRawValue> {
    return {
      ...programacion,
      desde: programacion.desde ? programacion.desde.format(DATE_TIME_FORMAT) : undefined,
      hasta: programacion.hasta ? programacion.hasta.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
