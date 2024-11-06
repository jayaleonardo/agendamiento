import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../tipo-terapia.test-samples';

import { TipoTerapiaFormService } from './tipo-terapia-form.service';

describe('TipoTerapia Form Service', () => {
  let service: TipoTerapiaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TipoTerapiaFormService);
  });

  describe('Service methods', () => {
    describe('createTipoTerapiaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTipoTerapiaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descripcion: expect.any(Object),
          }),
        );
      });

      it('passing ITipoTerapia should create a new form with FormGroup', () => {
        const formGroup = service.createTipoTerapiaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descripcion: expect.any(Object),
          }),
        );
      });
    });

    describe('getTipoTerapia', () => {
      it('should return NewTipoTerapia for default TipoTerapia initial value', () => {
        const formGroup = service.createTipoTerapiaFormGroup(sampleWithNewData);

        const tipoTerapia = service.getTipoTerapia(formGroup) as any;

        expect(tipoTerapia).toMatchObject(sampleWithNewData);
      });

      it('should return NewTipoTerapia for empty TipoTerapia initial value', () => {
        const formGroup = service.createTipoTerapiaFormGroup();

        const tipoTerapia = service.getTipoTerapia(formGroup) as any;

        expect(tipoTerapia).toMatchObject({});
      });

      it('should return ITipoTerapia', () => {
        const formGroup = service.createTipoTerapiaFormGroup(sampleWithRequiredData);

        const tipoTerapia = service.getTipoTerapia(formGroup) as any;

        expect(tipoTerapia).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITipoTerapia should not enable id FormControl', () => {
        const formGroup = service.createTipoTerapiaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTipoTerapia should disable id FormControl', () => {
        const formGroup = service.createTipoTerapiaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
