import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICita, NewCita } from '../cita.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICita for edit and NewCitaFormGroupInput for create.
 */
type CitaFormGroupInput = ICita | PartialWithRequiredKeyOf<NewCita>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICita | NewCita> = Omit<T, 'horaInicio'> & {
  horaInicio?: string | null;
};

type CitaFormRawValue = FormValueOf<ICita>;

type NewCitaFormRawValue = FormValueOf<NewCita>;

type CitaFormDefaults = Pick<NewCita, 'id' | 'horaInicio' | 'virtual'>;

type CitaFormGroupContent = {
  id: FormControl<CitaFormRawValue['id'] | NewCita['id']>;
  fechaCita: FormControl<CitaFormRawValue['fechaCita']>;
  horaInicio: FormControl<CitaFormRawValue['horaInicio']>;
  duracionMinutos: FormControl<CitaFormRawValue['duracionMinutos']>;
  estado: FormControl<CitaFormRawValue['estado']>;
  tipoVisita: FormControl<CitaFormRawValue['tipoVisita']>;
  canalAtencion: FormControl<CitaFormRawValue['canalAtencion']>;
  observacion: FormControl<CitaFormRawValue['observacion']>;
  recordatorio: FormControl<CitaFormRawValue['recordatorio']>;
  motivoConsulta: FormControl<CitaFormRawValue['motivoConsulta']>;
  detalleConsultaVirtual: FormControl<CitaFormRawValue['detalleConsultaVirtual']>;
  virtual: FormControl<CitaFormRawValue['virtual']>;
  informacionReserva: FormControl<CitaFormRawValue['informacionReserva']>;
  especialista: FormControl<CitaFormRawValue['especialista']>;
  tipoTerapia: FormControl<CitaFormRawValue['tipoTerapia']>;
  paciente: FormControl<CitaFormRawValue['paciente']>;
  programacion: FormControl<CitaFormRawValue['programacion']>;
};

export type CitaFormGroup = FormGroup<CitaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CitaFormService {
  createCitaFormGroup(cita: CitaFormGroupInput = { id: null }): CitaFormGroup {
    const citaRawValue = this.convertCitaToCitaRawValue({
      ...this.getFormDefaults(),
      ...cita,
    });
    return new FormGroup<CitaFormGroupContent>({
      id: new FormControl(
        { value: citaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fechaCita: new FormControl(citaRawValue.fechaCita, {
        validators: [Validators.required],
      }),
      horaInicio: new FormControl(citaRawValue.horaInicio, {
        validators: [Validators.required],
      }),
      duracionMinutos: new FormControl(citaRawValue.duracionMinutos, {
        validators: [Validators.required],
      }),
      estado: new FormControl(citaRawValue.estado),
      tipoVisita: new FormControl(citaRawValue.tipoVisita),
      canalAtencion: new FormControl(citaRawValue.canalAtencion),
      observacion: new FormControl(citaRawValue.observacion),
      recordatorio: new FormControl(citaRawValue.recordatorio),
      motivoConsulta: new FormControl(citaRawValue.motivoConsulta),
      detalleConsultaVirtual: new FormControl(citaRawValue.detalleConsultaVirtual),
      virtual: new FormControl(citaRawValue.virtual),
      informacionReserva: new FormControl(citaRawValue.informacionReserva),
      especialista: new FormControl(citaRawValue.especialista),
      tipoTerapia: new FormControl(citaRawValue.tipoTerapia),
      paciente: new FormControl(citaRawValue.paciente),
      programacion: new FormControl(citaRawValue.programacion),
    });
  }

  getCita(form: CitaFormGroup): ICita | NewCita {
    return this.convertCitaRawValueToCita(form.getRawValue() as CitaFormRawValue | NewCitaFormRawValue);
  }

  resetForm(form: CitaFormGroup, cita: CitaFormGroupInput): void {
    const citaRawValue = this.convertCitaToCitaRawValue({ ...this.getFormDefaults(), ...cita });
    form.reset(
      {
        ...citaRawValue,
        id: { value: citaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CitaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      horaInicio: currentTime,
      virtual: false,
    };
  }

  private convertCitaRawValueToCita(rawCita: CitaFormRawValue | NewCitaFormRawValue): ICita | NewCita {
    return {
      ...rawCita,
      horaInicio: dayjs(rawCita.horaInicio, DATE_TIME_FORMAT),
    };
  }

  private convertCitaToCitaRawValue(
    cita: ICita | (Partial<NewCita> & CitaFormDefaults),
  ): CitaFormRawValue | PartialWithRequiredKeyOf<NewCitaFormRawValue> {
    return {
      ...cita,
      horaInicio: cita.horaInicio ? cita.horaInicio.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
