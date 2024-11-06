import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IHorarioConsulta, NewHorarioConsulta } from '../horario-consulta.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IHorarioConsulta for edit and NewHorarioConsultaFormGroupInput for create.
 */
type HorarioConsultaFormGroupInput = IHorarioConsulta | PartialWithRequiredKeyOf<NewHorarioConsulta>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IHorarioConsulta | NewHorarioConsulta> = Omit<T, 'horaInicio'> & {
  horaInicio?: string | null;
};

type HorarioConsultaFormRawValue = FormValueOf<IHorarioConsulta>;

type NewHorarioConsultaFormRawValue = FormValueOf<NewHorarioConsulta>;

type HorarioConsultaFormDefaults = Pick<NewHorarioConsulta, 'id' | 'horaInicio' | 'esHorarioAtencion'>;

type HorarioConsultaFormGroupContent = {
  id: FormControl<HorarioConsultaFormRawValue['id'] | NewHorarioConsulta['id']>;
  fechaHorario: FormControl<HorarioConsultaFormRawValue['fechaHorario']>;
  horaInicio: FormControl<HorarioConsultaFormRawValue['horaInicio']>;
  duracionMinutos: FormControl<HorarioConsultaFormRawValue['duracionMinutos']>;
  diaSemana: FormControl<HorarioConsultaFormRawValue['diaSemana']>;
  esHorarioAtencion: FormControl<HorarioConsultaFormRawValue['esHorarioAtencion']>;
  estado: FormControl<HorarioConsultaFormRawValue['estado']>;
  especialista: FormControl<HorarioConsultaFormRawValue['especialista']>;
};

export type HorarioConsultaFormGroup = FormGroup<HorarioConsultaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class HorarioConsultaFormService {
  createHorarioConsultaFormGroup(horarioConsulta: HorarioConsultaFormGroupInput = { id: null }): HorarioConsultaFormGroup {
    const horarioConsultaRawValue = this.convertHorarioConsultaToHorarioConsultaRawValue({
      ...this.getFormDefaults(),
      ...horarioConsulta,
    });
    return new FormGroup<HorarioConsultaFormGroupContent>({
      id: new FormControl(
        { value: horarioConsultaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fechaHorario: new FormControl(horarioConsultaRawValue.fechaHorario, {
        validators: [Validators.required],
      }),
      horaInicio: new FormControl(horarioConsultaRawValue.horaInicio, {
        validators: [Validators.required],
      }),
      duracionMinutos: new FormControl(horarioConsultaRawValue.duracionMinutos, {
        validators: [Validators.required],
      }),
      diaSemana: new FormControl(horarioConsultaRawValue.diaSemana),
      esHorarioAtencion: new FormControl(horarioConsultaRawValue.esHorarioAtencion),
      estado: new FormControl(horarioConsultaRawValue.estado),
      especialista: new FormControl(horarioConsultaRawValue.especialista),
    });
  }

  getHorarioConsulta(form: HorarioConsultaFormGroup): IHorarioConsulta | NewHorarioConsulta {
    return this.convertHorarioConsultaRawValueToHorarioConsulta(
      form.getRawValue() as HorarioConsultaFormRawValue | NewHorarioConsultaFormRawValue,
    );
  }

  resetForm(form: HorarioConsultaFormGroup, horarioConsulta: HorarioConsultaFormGroupInput): void {
    const horarioConsultaRawValue = this.convertHorarioConsultaToHorarioConsultaRawValue({ ...this.getFormDefaults(), ...horarioConsulta });
    form.reset(
      {
        ...horarioConsultaRawValue,
        id: { value: horarioConsultaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): HorarioConsultaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      horaInicio: currentTime,
      esHorarioAtencion: false,
    };
  }

  private convertHorarioConsultaRawValueToHorarioConsulta(
    rawHorarioConsulta: HorarioConsultaFormRawValue | NewHorarioConsultaFormRawValue,
  ): IHorarioConsulta | NewHorarioConsulta {
    return {
      ...rawHorarioConsulta,
      horaInicio: dayjs(rawHorarioConsulta.horaInicio, DATE_TIME_FORMAT),
    };
  }

  private convertHorarioConsultaToHorarioConsultaRawValue(
    horarioConsulta: IHorarioConsulta | (Partial<NewHorarioConsulta> & HorarioConsultaFormDefaults),
  ): HorarioConsultaFormRawValue | PartialWithRequiredKeyOf<NewHorarioConsultaFormRawValue> {
    return {
      ...horarioConsulta,
      horaInicio: horarioConsulta.horaInicio ? horarioConsulta.horaInicio.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
