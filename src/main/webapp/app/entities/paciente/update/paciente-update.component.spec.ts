import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ISujeto } from 'app/entities/sujeto/sujeto.model';
import { SujetoService } from 'app/entities/sujeto/service/sujeto.service';
import { PacienteService } from '../service/paciente.service';
import { IPaciente } from '../paciente.model';
import { PacienteFormService } from './paciente-form.service';

import { PacienteUpdateComponent } from './paciente-update.component';

describe('Paciente Management Update Component', () => {
  let comp: PacienteUpdateComponent;
  let fixture: ComponentFixture<PacienteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pacienteFormService: PacienteFormService;
  let pacienteService: PacienteService;
  let sujetoService: SujetoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PacienteUpdateComponent],
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
      .overrideTemplate(PacienteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PacienteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pacienteFormService = TestBed.inject(PacienteFormService);
    pacienteService = TestBed.inject(PacienteService);
    sujetoService = TestBed.inject(SujetoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call sujeto query and add missing value', () => {
      const paciente: IPaciente = { id: 456 };
      const sujeto: ISujeto = { id: 865 };
      paciente.sujeto = sujeto;

      const sujetoCollection: ISujeto[] = [{ id: 25804 }];
      jest.spyOn(sujetoService, 'query').mockReturnValue(of(new HttpResponse({ body: sujetoCollection })));
      const expectedCollection: ISujeto[] = [sujeto, ...sujetoCollection];
      jest.spyOn(sujetoService, 'addSujetoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ paciente });
      comp.ngOnInit();

      expect(sujetoService.query).toHaveBeenCalled();
      expect(sujetoService.addSujetoToCollectionIfMissing).toHaveBeenCalledWith(sujetoCollection, sujeto);
      expect(comp.sujetosCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const paciente: IPaciente = { id: 456 };
      const sujeto: ISujeto = { id: 3904 };
      paciente.sujeto = sujeto;

      activatedRoute.data = of({ paciente });
      comp.ngOnInit();

      expect(comp.sujetosCollection).toContain(sujeto);
      expect(comp.paciente).toEqual(paciente);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaciente>>();
      const paciente = { id: 123 };
      jest.spyOn(pacienteFormService, 'getPaciente').mockReturnValue(paciente);
      jest.spyOn(pacienteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paciente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paciente }));
      saveSubject.complete();

      // THEN
      expect(pacienteFormService.getPaciente).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pacienteService.update).toHaveBeenCalledWith(expect.objectContaining(paciente));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaciente>>();
      const paciente = { id: 123 };
      jest.spyOn(pacienteFormService, 'getPaciente').mockReturnValue({ id: null });
      jest.spyOn(pacienteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paciente: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paciente }));
      saveSubject.complete();

      // THEN
      expect(pacienteFormService.getPaciente).toHaveBeenCalled();
      expect(pacienteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaciente>>();
      const paciente = { id: 123 };
      jest.spyOn(pacienteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paciente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pacienteService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSujeto', () => {
      it('Should forward to sujetoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(sujetoService, 'compareSujeto');
        comp.compareSujeto(entity, entity2);
        expect(sujetoService.compareSujeto).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
