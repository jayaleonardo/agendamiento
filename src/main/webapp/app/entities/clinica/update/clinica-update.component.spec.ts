import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ClinicaService } from '../service/clinica.service';
import { IClinica } from '../clinica.model';
import { ClinicaFormService } from './clinica-form.service';

import { ClinicaUpdateComponent } from './clinica-update.component';

describe('Clinica Management Update Component', () => {
  let comp: ClinicaUpdateComponent;
  let fixture: ComponentFixture<ClinicaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let clinicaFormService: ClinicaFormService;
  let clinicaService: ClinicaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ClinicaUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ClinicaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ClinicaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    clinicaFormService = TestBed.inject(ClinicaFormService);
    clinicaService = TestBed.inject(ClinicaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const clinica: IClinica = { id: 456 };

      activatedRoute.data = of({ clinica });
      comp.ngOnInit();

      expect(comp.clinica).toEqual(clinica);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClinica>>();
      const clinica = { id: 123 };
      jest.spyOn(clinicaFormService, 'getClinica').mockReturnValue(clinica);
      jest.spyOn(clinicaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ clinica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: clinica }));
      saveSubject.complete();

      // THEN
      expect(clinicaFormService.getClinica).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(clinicaService.update).toHaveBeenCalledWith(expect.objectContaining(clinica));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClinica>>();
      const clinica = { id: 123 };
      jest.spyOn(clinicaFormService, 'getClinica').mockReturnValue({ id: null });
      jest.spyOn(clinicaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ clinica: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: clinica }));
      saveSubject.complete();

      // THEN
      expect(clinicaFormService.getClinica).toHaveBeenCalled();
      expect(clinicaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClinica>>();
      const clinica = { id: 123 };
      jest.spyOn(clinicaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ clinica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(clinicaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
