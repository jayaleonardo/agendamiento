import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IHorarioConsulta } from 'app/entities/horario-consulta/horario-consulta.model';
import { HorarioConsultaService } from 'app/entities/horario-consulta/service/horario-consulta.service';
import { ProgramacionService } from '../service/programacion.service';
import { IProgramacion } from '../programacion.model';
import { ProgramacionFormService } from './programacion-form.service';

import { ProgramacionUpdateComponent } from './programacion-update.component';

describe('Programacion Management Update Component', () => {
  let comp: ProgramacionUpdateComponent;
  let fixture: ComponentFixture<ProgramacionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let programacionFormService: ProgramacionFormService;
  let programacionService: ProgramacionService;
  let horarioConsultaService: HorarioConsultaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ProgramacionUpdateComponent],
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
      .overrideTemplate(ProgramacionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProgramacionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    programacionFormService = TestBed.inject(ProgramacionFormService);
    programacionService = TestBed.inject(ProgramacionService);
    horarioConsultaService = TestBed.inject(HorarioConsultaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call HorarioConsulta query and add missing value', () => {
      const programacion: IProgramacion = { id: 456 };
      const horarioConsulta: IHorarioConsulta = { id: 14181 };
      programacion.horarioConsulta = horarioConsulta;

      const horarioConsultaCollection: IHorarioConsulta[] = [{ id: 4488 }];
      jest.spyOn(horarioConsultaService, 'query').mockReturnValue(of(new HttpResponse({ body: horarioConsultaCollection })));
      const additionalHorarioConsultas = [horarioConsulta];
      const expectedCollection: IHorarioConsulta[] = [...additionalHorarioConsultas, ...horarioConsultaCollection];
      jest.spyOn(horarioConsultaService, 'addHorarioConsultaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programacion });
      comp.ngOnInit();

      expect(horarioConsultaService.query).toHaveBeenCalled();
      expect(horarioConsultaService.addHorarioConsultaToCollectionIfMissing).toHaveBeenCalledWith(
        horarioConsultaCollection,
        ...additionalHorarioConsultas.map(expect.objectContaining),
      );
      expect(comp.horarioConsultasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const programacion: IProgramacion = { id: 456 };
      const horarioConsulta: IHorarioConsulta = { id: 14641 };
      programacion.horarioConsulta = horarioConsulta;

      activatedRoute.data = of({ programacion });
      comp.ngOnInit();

      expect(comp.horarioConsultasSharedCollection).toContain(horarioConsulta);
      expect(comp.programacion).toEqual(programacion);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgramacion>>();
      const programacion = { id: 123 };
      jest.spyOn(programacionFormService, 'getProgramacion').mockReturnValue(programacion);
      jest.spyOn(programacionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programacion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: programacion }));
      saveSubject.complete();

      // THEN
      expect(programacionFormService.getProgramacion).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(programacionService.update).toHaveBeenCalledWith(expect.objectContaining(programacion));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgramacion>>();
      const programacion = { id: 123 };
      jest.spyOn(programacionFormService, 'getProgramacion').mockReturnValue({ id: null });
      jest.spyOn(programacionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programacion: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: programacion }));
      saveSubject.complete();

      // THEN
      expect(programacionFormService.getProgramacion).toHaveBeenCalled();
      expect(programacionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgramacion>>();
      const programacion = { id: 123 };
      jest.spyOn(programacionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programacion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(programacionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareHorarioConsulta', () => {
      it('Should forward to horarioConsultaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(horarioConsultaService, 'compareHorarioConsulta');
        comp.compareHorarioConsulta(entity, entity2);
        expect(horarioConsultaService.compareHorarioConsulta).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
