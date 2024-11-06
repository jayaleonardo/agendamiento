import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../sujeto.test-samples';

import { SujetoFormService } from './sujeto-form.service';

describe('Sujeto Form Service', () => {
  let service: SujetoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SujetoFormService);
  });

  describe('Service methods', () => {
    describe('createSujetoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSujetoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            segundoNombre: expect.any(Object),
            apellido: expect.any(Object),
            segundoApellido: expect.any(Object),
            documentoIdentidad: expect.any(Object),
            estado: expect.any(Object),
            sexo: expect.any(Object),
            fechaNacimiento: expect.any(Object),
          }),
        );
      });

      it('passing ISujeto should create a new form with FormGroup', () => {
        const formGroup = service.createSujetoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            segundoNombre: expect.any(Object),
            apellido: expect.any(Object),
            segundoApellido: expect.any(Object),
            documentoIdentidad: expect.any(Object),
            estado: expect.any(Object),
            sexo: expect.any(Object),
            fechaNacimiento: expect.any(Object),
          }),
        );
      });
    });

    describe('getSujeto', () => {
      it('should return NewSujeto for default Sujeto initial value', () => {
        const formGroup = service.createSujetoFormGroup(sampleWithNewData);

        const sujeto = service.getSujeto(formGroup) as any;

        expect(sujeto).toMatchObject(sampleWithNewData);
      });

      it('should return NewSujeto for empty Sujeto initial value', () => {
        const formGroup = service.createSujetoFormGroup();

        const sujeto = service.getSujeto(formGroup) as any;

        expect(sujeto).toMatchObject({});
      });

      it('should return ISujeto', () => {
        const formGroup = service.createSujetoFormGroup(sampleWithRequiredData);

        const sujeto = service.getSujeto(formGroup) as any;

        expect(sujeto).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISujeto should not enable id FormControl', () => {
        const formGroup = service.createSujetoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSujeto should disable id FormControl', () => {
        const formGroup = service.createSujetoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
