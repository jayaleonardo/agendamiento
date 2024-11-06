import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../contacto.test-samples';

import { ContactoFormService } from './contacto-form.service';

describe('Contacto Form Service', () => {
  let service: ContactoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContactoFormService);
  });

  describe('Service methods', () => {
    describe('createContactoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createContactoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            telefono: expect.any(Object),
            correo: expect.any(Object),
            codigoPais: expect.any(Object),
            celular: expect.any(Object),
            sujeto: expect.any(Object),
          }),
        );
      });

      it('passing IContacto should create a new form with FormGroup', () => {
        const formGroup = service.createContactoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            telefono: expect.any(Object),
            correo: expect.any(Object),
            codigoPais: expect.any(Object),
            celular: expect.any(Object),
            sujeto: expect.any(Object),
          }),
        );
      });
    });

    describe('getContacto', () => {
      it('should return NewContacto for default Contacto initial value', () => {
        const formGroup = service.createContactoFormGroup(sampleWithNewData);

        const contacto = service.getContacto(formGroup) as any;

        expect(contacto).toMatchObject(sampleWithNewData);
      });

      it('should return NewContacto for empty Contacto initial value', () => {
        const formGroup = service.createContactoFormGroup();

        const contacto = service.getContacto(formGroup) as any;

        expect(contacto).toMatchObject({});
      });

      it('should return IContacto', () => {
        const formGroup = service.createContactoFormGroup(sampleWithRequiredData);

        const contacto = service.getContacto(formGroup) as any;

        expect(contacto).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IContacto should not enable id FormControl', () => {
        const formGroup = service.createContactoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewContacto should disable id FormControl', () => {
        const formGroup = service.createContactoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
