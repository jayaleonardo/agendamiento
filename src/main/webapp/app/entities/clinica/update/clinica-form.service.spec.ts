import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../clinica.test-samples';

import { ClinicaFormService } from './clinica-form.service';

describe('Clinica Form Service', () => {
  let service: ClinicaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ClinicaFormService);
  });

  describe('Service methods', () => {
    describe('createClinicaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createClinicaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            telefono: expect.any(Object),
            correo: expect.any(Object),
            horarioAtencion: expect.any(Object),
          }),
        );
      });

      it('passing IClinica should create a new form with FormGroup', () => {
        const formGroup = service.createClinicaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            telefono: expect.any(Object),
            correo: expect.any(Object),
            horarioAtencion: expect.any(Object),
          }),
        );
      });
    });

    describe('getClinica', () => {
      it('should return NewClinica for default Clinica initial value', () => {
        const formGroup = service.createClinicaFormGroup(sampleWithNewData);

        const clinica = service.getClinica(formGroup) as any;

        expect(clinica).toMatchObject(sampleWithNewData);
      });

      it('should return NewClinica for empty Clinica initial value', () => {
        const formGroup = service.createClinicaFormGroup();

        const clinica = service.getClinica(formGroup) as any;

        expect(clinica).toMatchObject({});
      });

      it('should return IClinica', () => {
        const formGroup = service.createClinicaFormGroup(sampleWithRequiredData);

        const clinica = service.getClinica(formGroup) as any;

        expect(clinica).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IClinica should not enable id FormControl', () => {
        const formGroup = service.createClinicaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewClinica should disable id FormControl', () => {
        const formGroup = service.createClinicaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
