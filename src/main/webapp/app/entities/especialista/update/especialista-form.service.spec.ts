import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../especialista.test-samples';

import { EspecialistaFormService } from './especialista-form.service';

describe('Especialista Form Service', () => {
  let service: EspecialistaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EspecialistaFormService);
  });

  describe('Service methods', () => {
    describe('createEspecialistaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEspecialistaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            especialidad: expect.any(Object),
            codigoDoctor: expect.any(Object),
            nroConsultorio: expect.any(Object),
            fotoUrl: expect.any(Object),
            sujeto: expect.any(Object),
          }),
        );
      });

      it('passing IEspecialista should create a new form with FormGroup', () => {
        const formGroup = service.createEspecialistaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            especialidad: expect.any(Object),
            codigoDoctor: expect.any(Object),
            nroConsultorio: expect.any(Object),
            fotoUrl: expect.any(Object),
            sujeto: expect.any(Object),
          }),
        );
      });
    });

    describe('getEspecialista', () => {
      it('should return NewEspecialista for default Especialista initial value', () => {
        const formGroup = service.createEspecialistaFormGroup(sampleWithNewData);

        const especialista = service.getEspecialista(formGroup) as any;

        expect(especialista).toMatchObject(sampleWithNewData);
      });

      it('should return NewEspecialista for empty Especialista initial value', () => {
        const formGroup = service.createEspecialistaFormGroup();

        const especialista = service.getEspecialista(formGroup) as any;

        expect(especialista).toMatchObject({});
      });

      it('should return IEspecialista', () => {
        const formGroup = service.createEspecialistaFormGroup(sampleWithRequiredData);

        const especialista = service.getEspecialista(formGroup) as any;

        expect(especialista).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEspecialista should not enable id FormControl', () => {
        const formGroup = service.createEspecialistaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEspecialista should disable id FormControl', () => {
        const formGroup = service.createEspecialistaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
