import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IEspecialista } from 'app/entities/especialista/especialista.model';
import { EspecialistaService } from 'app/entities/especialista/service/especialista.service';
import { HorarioConsultaService } from '../service/horario-consulta.service';
import { IHorarioConsulta } from '../horario-consulta.model';
import { HorarioConsultaFormService } from './horario-consulta-form.service';

import { HorarioConsultaUpdateComponent } from './horario-consulta-update.component';

describe('HorarioConsulta Management Update Component', () => {
  let comp: HorarioConsultaUpdateComponent;
  let fixture: ComponentFixture<HorarioConsultaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let horarioConsultaFormService: HorarioConsultaFormService;
  let horarioConsultaService: HorarioConsultaService;
  let especialistaService: EspecialistaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HorarioConsultaUpdateComponent],
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
      .overrideTemplate(HorarioConsultaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HorarioConsultaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    horarioConsultaFormService = TestBed.inject(HorarioConsultaFormService);
    horarioConsultaService = TestBed.inject(HorarioConsultaService);
    especialistaService = TestBed.inject(EspecialistaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Especialista query and add missing value', () => {
      const horarioConsulta: IHorarioConsulta = { id: 456 };
      const especialista: IEspecialista = { id: 28186 };
      horarioConsulta.especialista = especialista;

      const especialistaCollection: IEspecialista[] = [{ id: 20215 }];
      jest.spyOn(especialistaService, 'query').mockReturnValue(of(new HttpResponse({ body: especialistaCollection })));
      const additionalEspecialistas = [especialista];
      const expectedCollection: IEspecialista[] = [...additionalEspecialistas, ...especialistaCollection];
      jest.spyOn(especialistaService, 'addEspecialistaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ horarioConsulta });
      comp.ngOnInit();

      expect(especialistaService.query).toHaveBeenCalled();
      expect(especialistaService.addEspecialistaToCollectionIfMissing).toHaveBeenCalledWith(
        especialistaCollection,
        ...additionalEspecialistas.map(expect.objectContaining),
      );
      expect(comp.especialistasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const horarioConsulta: IHorarioConsulta = { id: 456 };
      const especialista: IEspecialista = { id: 27185 };
      horarioConsulta.especialista = especialista;

      activatedRoute.data = of({ horarioConsulta });
      comp.ngOnInit();

      expect(comp.especialistasSharedCollection).toContain(especialista);
      expect(comp.horarioConsulta).toEqual(horarioConsulta);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHorarioConsulta>>();
      const horarioConsulta = { id: 123 };
      jest.spyOn(horarioConsultaFormService, 'getHorarioConsulta').mockReturnValue(horarioConsulta);
      jest.spyOn(horarioConsultaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ horarioConsulta });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: horarioConsulta }));
      saveSubject.complete();

      // THEN
      expect(horarioConsultaFormService.getHorarioConsulta).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(horarioConsultaService.update).toHaveBeenCalledWith(expect.objectContaining(horarioConsulta));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHorarioConsulta>>();
      const horarioConsulta = { id: 123 };
      jest.spyOn(horarioConsultaFormService, 'getHorarioConsulta').mockReturnValue({ id: null });
      jest.spyOn(horarioConsultaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ horarioConsulta: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: horarioConsulta }));
      saveSubject.complete();

      // THEN
      expect(horarioConsultaFormService.getHorarioConsulta).toHaveBeenCalled();
      expect(horarioConsultaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHorarioConsulta>>();
      const horarioConsulta = { id: 123 };
      jest.spyOn(horarioConsultaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ horarioConsulta });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(horarioConsultaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEspecialista', () => {
      it('Should forward to especialistaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(especialistaService, 'compareEspecialista');
        comp.compareEspecialista(entity, entity2);
        expect(especialistaService.compareEspecialista).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
