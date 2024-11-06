import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IEspecialista } from 'app/entities/especialista/especialista.model';
import { EspecialistaService } from 'app/entities/especialista/service/especialista.service';
import { ITipoTerapia } from 'app/entities/tipo-terapia/tipo-terapia.model';
import { TipoTerapiaService } from 'app/entities/tipo-terapia/service/tipo-terapia.service';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { PacienteService } from 'app/entities/paciente/service/paciente.service';
import { ICita } from '../cita.model';
import { CitaService } from '../service/cita.service';
import { CitaFormService } from './cita-form.service';

import { CitaUpdateComponent } from './cita-update.component';

describe('Cita Management Update Component', () => {
  let comp: CitaUpdateComponent;
  let fixture: ComponentFixture<CitaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let citaFormService: CitaFormService;
  let citaService: CitaService;
  let especialistaService: EspecialistaService;
  let tipoTerapiaService: TipoTerapiaService;
  let pacienteService: PacienteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CitaUpdateComponent],
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
      .overrideTemplate(CitaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CitaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    citaFormService = TestBed.inject(CitaFormService);
    citaService = TestBed.inject(CitaService);
    especialistaService = TestBed.inject(EspecialistaService);
    tipoTerapiaService = TestBed.inject(TipoTerapiaService);
    pacienteService = TestBed.inject(PacienteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Especialista query and add missing value', () => {
      const cita: ICita = { id: 456 };
      const especialista: IEspecialista = { id: 30925 };
      cita.especialista = especialista;

      const especialistaCollection: IEspecialista[] = [{ id: 13229 }];
      jest.spyOn(especialistaService, 'query').mockReturnValue(of(new HttpResponse({ body: especialistaCollection })));
      const additionalEspecialistas = [especialista];
      const expectedCollection: IEspecialista[] = [...additionalEspecialistas, ...especialistaCollection];
      jest.spyOn(especialistaService, 'addEspecialistaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ cita });
      comp.ngOnInit();

      expect(especialistaService.query).toHaveBeenCalled();
      expect(especialistaService.addEspecialistaToCollectionIfMissing).toHaveBeenCalledWith(
        especialistaCollection,
        ...additionalEspecialistas.map(expect.objectContaining),
      );
      expect(comp.especialistasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TipoTerapia query and add missing value', () => {
      const cita: ICita = { id: 456 };
      const tipoTerapia: ITipoTerapia = { id: 2311 };
      cita.tipoTerapia = tipoTerapia;

      const tipoTerapiaCollection: ITipoTerapia[] = [{ id: 27128 }];
      jest.spyOn(tipoTerapiaService, 'query').mockReturnValue(of(new HttpResponse({ body: tipoTerapiaCollection })));
      const additionalTipoTerapias = [tipoTerapia];
      const expectedCollection: ITipoTerapia[] = [...additionalTipoTerapias, ...tipoTerapiaCollection];
      jest.spyOn(tipoTerapiaService, 'addTipoTerapiaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ cita });
      comp.ngOnInit();

      expect(tipoTerapiaService.query).toHaveBeenCalled();
      expect(tipoTerapiaService.addTipoTerapiaToCollectionIfMissing).toHaveBeenCalledWith(
        tipoTerapiaCollection,
        ...additionalTipoTerapias.map(expect.objectContaining),
      );
      expect(comp.tipoTerapiasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Paciente query and add missing value', () => {
      const cita: ICita = { id: 456 };
      const paciente: IPaciente = { id: 17623 };
      cita.paciente = paciente;

      const pacienteCollection: IPaciente[] = [{ id: 10825 }];
      jest.spyOn(pacienteService, 'query').mockReturnValue(of(new HttpResponse({ body: pacienteCollection })));
      const additionalPacientes = [paciente];
      const expectedCollection: IPaciente[] = [...additionalPacientes, ...pacienteCollection];
      jest.spyOn(pacienteService, 'addPacienteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ cita });
      comp.ngOnInit();

      expect(pacienteService.query).toHaveBeenCalled();
      expect(pacienteService.addPacienteToCollectionIfMissing).toHaveBeenCalledWith(
        pacienteCollection,
        ...additionalPacientes.map(expect.objectContaining),
      );
      expect(comp.pacientesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const cita: ICita = { id: 456 };
      const especialista: IEspecialista = { id: 24257 };
      cita.especialista = especialista;
      const tipoTerapia: ITipoTerapia = { id: 32167 };
      cita.tipoTerapia = tipoTerapia;
      const paciente: IPaciente = { id: 9979 };
      cita.paciente = paciente;

      activatedRoute.data = of({ cita });
      comp.ngOnInit();

      expect(comp.especialistasSharedCollection).toContain(especialista);
      expect(comp.tipoTerapiasSharedCollection).toContain(tipoTerapia);
      expect(comp.pacientesSharedCollection).toContain(paciente);
      expect(comp.cita).toEqual(cita);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICita>>();
      const cita = { id: 123 };
      jest.spyOn(citaFormService, 'getCita').mockReturnValue(cita);
      jest.spyOn(citaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cita });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cita }));
      saveSubject.complete();

      // THEN
      expect(citaFormService.getCita).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(citaService.update).toHaveBeenCalledWith(expect.objectContaining(cita));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICita>>();
      const cita = { id: 123 };
      jest.spyOn(citaFormService, 'getCita').mockReturnValue({ id: null });
      jest.spyOn(citaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cita: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cita }));
      saveSubject.complete();

      // THEN
      expect(citaFormService.getCita).toHaveBeenCalled();
      expect(citaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICita>>();
      const cita = { id: 123 };
      jest.spyOn(citaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cita });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(citaService.update).toHaveBeenCalled();
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

    describe('compareTipoTerapia', () => {
      it('Should forward to tipoTerapiaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tipoTerapiaService, 'compareTipoTerapia');
        comp.compareTipoTerapia(entity, entity2);
        expect(tipoTerapiaService.compareTipoTerapia).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePaciente', () => {
      it('Should forward to pacienteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pacienteService, 'comparePaciente');
        comp.comparePaciente(entity, entity2);
        expect(pacienteService.comparePaciente).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
