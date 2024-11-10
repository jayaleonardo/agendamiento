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
type FormValueOf<T extends IHorarioConsulta | NewHorarioConsulta> = Omit<
  T,
  'horaInicio' | 'horaFin' | 'desdeHoraAlmuerzo' | 'hastaHoraAlmuerzo'
> & {
  horaInicio?: string | null;
  horaFin?: string | null;
  desdeHoraAlmuerzo?: string | null;
  hastaHoraAlmuerzo?: string | null;
};

type HorarioConsultaFormRawValue = FormValueOf<IHorarioConsulta>;

type NewHorarioConsultaFormRawValue = FormValueOf<NewHorarioConsulta>;

type HorarioConsultaFormDefaults = Pick<
  NewHorarioConsulta,
  'id' | 'horaInicio' | 'horaFin' | 'esHorarioAtencion' | 'desdeHoraAlmuerzo' | 'hastaHoraAlmuerzo'
>;

type HorarioConsultaFormGroupContent = {
  id: FormControl<HorarioConsultaFormRawValue['id'] | NewHorarioConsulta['id']>;
  desde: FormControl<HorarioConsultaFormRawValue['desde']>;
  hasta: FormControl<HorarioConsultaFormRawValue['hasta']>;
  horaInicio: FormControl<HorarioConsultaFormRawValue['horaInicio']>;
  horaFin: FormControl<HorarioConsultaFormRawValue['horaFin']>;
  duracionMinutos: FormControl<HorarioConsultaFormRawValue['duracionMinutos']>;
  diaSemana: FormControl<HorarioConsultaFormRawValue['diaSemana']>;
  esHorarioAtencion: FormControl<HorarioConsultaFormRawValue['esHorarioAtencion']>;
  estado: FormControl<HorarioConsultaFormRawValue['estado']>;
  desdeHoraAlmuerzo: FormControl<HorarioConsultaFormRawValue['desdeHoraAlmuerzo']>;
  hastaHoraAlmuerzo: FormControl<HorarioConsultaFormRawValue['hastaHoraAlmuerzo']>;
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
      desde: new FormControl(horarioConsultaRawValue.desde, {
        validators: [Validators.required],
      }),
      hasta: new FormControl(horarioConsultaRawValue.hasta, {
        validators: [Validators.required],
      }),
      horaInicio: new FormControl(horarioConsultaRawValue.horaInicio, {
        validators: [Validators.required],
      }),
      horaFin: new FormControl(horarioConsultaRawValue.horaFin, {
        validators: [Validators.required],
      }),
      duracionMinutos: new FormControl(horarioConsultaRawValue.duracionMinutos, {
        validators: [Validators.required],
      }),
      diaSemana: new FormControl(horarioConsultaRawValue.diaSemana),
      esHorarioAtencion: new FormControl(horarioConsultaRawValue.esHorarioAtencion),
      estado: new FormControl(horarioConsultaRawValue.estado),
      desdeHoraAlmuerzo: new FormControl(horarioConsultaRawValue.desdeHoraAlmuerzo),
      hastaHoraAlmuerzo: new FormControl(horarioConsultaRawValue.hastaHoraAlmuerzo),
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
      horaFin: currentTime,
      esHorarioAtencion: false,
      desdeHoraAlmuerzo: currentTime,
      hastaHoraAlmuerzo: currentTime,
    };
  }

  private convertHorarioConsultaRawValueToHorarioConsulta(
    rawHorarioConsulta: HorarioConsultaFormRawValue | NewHorarioConsultaFormRawValue,
  ): IHorarioConsulta | NewHorarioConsulta {
    return {
      ...rawHorarioConsulta,
      horaInicio: dayjs(rawHorarioConsulta.horaInicio, DATE_TIME_FORMAT),
      horaFin: dayjs(rawHorarioConsulta.horaFin, DATE_TIME_FORMAT),
      desdeHoraAlmuerzo: dayjs(rawHorarioConsulta.desdeHoraAlmuerzo, DATE_TIME_FORMAT),
      hastaHoraAlmuerzo: dayjs(rawHorarioConsulta.hastaHoraAlmuerzo, DATE_TIME_FORMAT),
    };
  }

  private convertHorarioConsultaToHorarioConsultaRawValue(
    horarioConsulta: IHorarioConsulta | (Partial<NewHorarioConsulta> & HorarioConsultaFormDefaults),
  ): HorarioConsultaFormRawValue | PartialWithRequiredKeyOf<NewHorarioConsultaFormRawValue> {
    return {
      ...horarioConsulta,
      horaInicio: horarioConsulta.horaInicio ? horarioConsulta.horaInicio.format(DATE_TIME_FORMAT) : undefined,
      horaFin: horarioConsulta.horaFin ? horarioConsulta.horaFin.format(DATE_TIME_FORMAT) : undefined,
      desdeHoraAlmuerzo: horarioConsulta.desdeHoraAlmuerzo ? horarioConsulta.desdeHoraAlmuerzo.format(DATE_TIME_FORMAT) : undefined,
      hastaHoraAlmuerzo: horarioConsulta.hastaHoraAlmuerzo ? horarioConsulta.hastaHoraAlmuerzo.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
