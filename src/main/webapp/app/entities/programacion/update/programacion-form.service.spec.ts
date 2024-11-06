import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../programacion.test-samples';

import { ProgramacionFormService } from './programacion-form.service';

describe('Programacion Form Service', () => {
  let service: ProgramacionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProgramacionFormService);
  });

  describe('Service methods', () => {
    describe('createProgramacionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProgramacionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fechaDesde: expect.any(Object),
            fechaHasta: expect.any(Object),
            duracionMinutos: expect.any(Object),
            desdeHoraAlmuerzo: expect.any(Object),
            hastaHoraAlmuerzo: expect.any(Object),
            diasSemana: expect.any(Object),
            horarioConsulta: expect.any(Object),
          }),
        );
      });

      it('passing IProgramacion should create a new form with FormGroup', () => {
        const formGroup = service.createProgramacionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fechaDesde: expect.any(Object),
            fechaHasta: expect.any(Object),
            duracionMinutos: expect.any(Object),
            desdeHoraAlmuerzo: expect.any(Object),
            hastaHoraAlmuerzo: expect.any(Object),
            diasSemana: expect.any(Object),
            horarioConsulta: expect.any(Object),
          }),
        );
      });
    });

    describe('getProgramacion', () => {
      it('should return NewProgramacion for default Programacion initial value', () => {
        const formGroup = service.createProgramacionFormGroup(sampleWithNewData);

        const programacion = service.getProgramacion(formGroup) as any;

        expect(programacion).toMatchObject(sampleWithNewData);
      });

      it('should return NewProgramacion for empty Programacion initial value', () => {
        const formGroup = service.createProgramacionFormGroup();

        const programacion = service.getProgramacion(formGroup) as any;

        expect(programacion).toMatchObject({});
      });

      it('should return IProgramacion', () => {
        const formGroup = service.createProgramacionFormGroup(sampleWithRequiredData);

        const programacion = service.getProgramacion(formGroup) as any;

        expect(programacion).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProgramacion should not enable id FormControl', () => {
        const formGroup = service.createProgramacionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProgramacion should disable id FormControl', () => {
        const formGroup = service.createProgramacionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
