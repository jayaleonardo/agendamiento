import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ISujeto } from 'app/entities/sujeto/sujeto.model';
import { SujetoService } from 'app/entities/sujeto/service/sujeto.service';
import { DireccionService } from '../service/direccion.service';
import { IDireccion } from '../direccion.model';
import { DireccionFormService } from './direccion-form.service';

import { DireccionUpdateComponent } from './direccion-update.component';

describe('Direccion Management Update Component', () => {
  let comp: DireccionUpdateComponent;
  let fixture: ComponentFixture<DireccionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let direccionFormService: DireccionFormService;
  let direccionService: DireccionService;
  let sujetoService: SujetoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DireccionUpdateComponent],
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
      .overrideTemplate(DireccionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DireccionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    direccionFormService = TestBed.inject(DireccionFormService);
    direccionService = TestBed.inject(DireccionService);
    sujetoService = TestBed.inject(SujetoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call sujeto query and add missing value', () => {
      const direccion: IDireccion = { id: 456 };
      const sujeto: ISujeto = { id: 16376 };
      direccion.sujeto = sujeto;

      const sujetoCollection: ISujeto[] = [{ id: 23340 }];
      jest.spyOn(sujetoService, 'query').mockReturnValue(of(new HttpResponse({ body: sujetoCollection })));
      const expectedCollection: ISujeto[] = [sujeto, ...sujetoCollection];
      jest.spyOn(sujetoService, 'addSujetoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ direccion });
      comp.ngOnInit();

      expect(sujetoService.query).toHaveBeenCalled();
      expect(sujetoService.addSujetoToCollectionIfMissing).toHaveBeenCalledWith(sujetoCollection, sujeto);
      expect(comp.sujetosCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const direccion: IDireccion = { id: 456 };
      const sujeto: ISujeto = { id: 5156 };
      direccion.sujeto = sujeto;

      activatedRoute.data = of({ direccion });
      comp.ngOnInit();

      expect(comp.sujetosCollection).toContain(sujeto);
      expect(comp.direccion).toEqual(direccion);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDireccion>>();
      const direccion = { id: 123 };
      jest.spyOn(direccionFormService, 'getDireccion').mockReturnValue(direccion);
      jest.spyOn(direccionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ direccion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: direccion }));
      saveSubject.complete();

      // THEN
      expect(direccionFormService.getDireccion).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(direccionService.update).toHaveBeenCalledWith(expect.objectContaining(direccion));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDireccion>>();
      const direccion = { id: 123 };
      jest.spyOn(direccionFormService, 'getDireccion').mockReturnValue({ id: null });
      jest.spyOn(direccionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ direccion: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: direccion }));
      saveSubject.complete();

      // THEN
      expect(direccionFormService.getDireccion).toHaveBeenCalled();
      expect(direccionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDireccion>>();
      const direccion = { id: 123 };
      jest.spyOn(direccionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ direccion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(direccionService.update).toHaveBeenCalled();
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
