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
type FormValueOf<T extends IProgramacion | NewProgramacion> = Omit<T, 'desdeHoraAlmuerzo' | 'hastaHoraAlmuerzo'> & {
  desdeHoraAlmuerzo?: string | null;
  hastaHoraAlmuerzo?: string | null;
};

type ProgramacionFormRawValue = FormValueOf<IProgramacion>;

type NewProgramacionFormRawValue = FormValueOf<NewProgramacion>;

type ProgramacionFormDefaults = Pick<NewProgramacion, 'id' | 'desdeHoraAlmuerzo' | 'hastaHoraAlmuerzo'>;

type ProgramacionFormGroupContent = {
  id: FormControl<ProgramacionFormRawValue['id'] | NewProgramacion['id']>;
  fechaDesde: FormControl<ProgramacionFormRawValue['fechaDesde']>;
  fechaHasta: FormControl<ProgramacionFormRawValue['fechaHasta']>;
  duracionMinutos: FormControl<ProgramacionFormRawValue['duracionMinutos']>;
  desdeHoraAlmuerzo: FormControl<ProgramacionFormRawValue['desdeHoraAlmuerzo']>;
  hastaHoraAlmuerzo: FormControl<ProgramacionFormRawValue['hastaHoraAlmuerzo']>;
  diasSemana: FormControl<ProgramacionFormRawValue['diasSemana']>;
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
      fechaDesde: new FormControl(programacionRawValue.fechaDesde, {
        validators: [Validators.required],
      }),
      fechaHasta: new FormControl(programacionRawValue.fechaHasta, {
        validators: [Validators.required],
      }),
      duracionMinutos: new FormControl(programacionRawValue.duracionMinutos, {
        validators: [Validators.required],
      }),
      desdeHoraAlmuerzo: new FormControl(programacionRawValue.desdeHoraAlmuerzo),
      hastaHoraAlmuerzo: new FormControl(programacionRawValue.hastaHoraAlmuerzo),
      diasSemana: new FormControl(programacionRawValue.diasSemana),
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
      desdeHoraAlmuerzo: currentTime,
      hastaHoraAlmuerzo: currentTime,
    };
  }

  private convertProgramacionRawValueToProgramacion(
    rawProgramacion: ProgramacionFormRawValue | NewProgramacionFormRawValue,
  ): IProgramacion | NewProgramacion {
    return {
      ...rawProgramacion,
      desdeHoraAlmuerzo: dayjs(rawProgramacion.desdeHoraAlmuerzo, DATE_TIME_FORMAT),
      hastaHoraAlmuerzo: dayjs(rawProgramacion.hastaHoraAlmuerzo, DATE_TIME_FORMAT),
    };
  }

  private convertProgramacionToProgramacionRawValue(
    programacion: IProgramacion | (Partial<NewProgramacion> & ProgramacionFormDefaults),
  ): ProgramacionFormRawValue | PartialWithRequiredKeyOf<NewProgramacionFormRawValue> {
    return {
      ...programacion,
      desdeHoraAlmuerzo: programacion.desdeHoraAlmuerzo ? programacion.desdeHoraAlmuerzo.format(DATE_TIME_FORMAT) : undefined,
      hastaHoraAlmuerzo: programacion.hastaHoraAlmuerzo ? programacion.hastaHoraAlmuerzo.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
