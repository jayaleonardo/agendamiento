import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../horario-consulta.test-samples';

import { HorarioConsultaFormService } from './horario-consulta-form.service';

describe('HorarioConsulta Form Service', () => {
  let service: HorarioConsultaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HorarioConsultaFormService);
  });

  describe('Service methods', () => {
    describe('createHorarioConsultaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createHorarioConsultaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fechaHorario: expect.any(Object),
            horaInicio: expect.any(Object),
            duracionMinutos: expect.any(Object),
            diaSemana: expect.any(Object),
            esHorarioAtencion: expect.any(Object),
            estado: expect.any(Object),
            especialista: expect.any(Object),
          }),
        );
      });

      it('passing IHorarioConsulta should create a new form with FormGroup', () => {
        const formGroup = service.createHorarioConsultaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fechaHorario: expect.any(Object),
            horaInicio: expect.any(Object),
            duracionMinutos: expect.any(Object),
            diaSemana: expect.any(Object),
            esHorarioAtencion: expect.any(Object),
            estado: expect.any(Object),
            especialista: expect.any(Object),
          }),
        );
      });
    });

    describe('getHorarioConsulta', () => {
      it('should return NewHorarioConsulta for default HorarioConsulta initial value', () => {
        const formGroup = service.createHorarioConsultaFormGroup(sampleWithNewData);

        const horarioConsulta = service.getHorarioConsulta(formGroup) as any;

        expect(horarioConsulta).toMatchObject(sampleWithNewData);
      });

      it('should return NewHorarioConsulta for empty HorarioConsulta initial value', () => {
        const formGroup = service.createHorarioConsultaFormGroup();

        const horarioConsulta = service.getHorarioConsulta(formGroup) as any;

        expect(horarioConsulta).toMatchObject({});
      });

      it('should return IHorarioConsulta', () => {
        const formGroup = service.createHorarioConsultaFormGroup(sampleWithRequiredData);

        const horarioConsulta = service.getHorarioConsulta(formGroup) as any;

        expect(horarioConsulta).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IHorarioConsulta should not enable id FormControl', () => {
        const formGroup = service.createHorarioConsultaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewHorarioConsulta should disable id FormControl', () => {
        const formGroup = service.createHorarioConsultaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
