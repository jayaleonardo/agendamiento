import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { TipoTerapiaService } from '../service/tipo-terapia.service';
import { ITipoTerapia } from '../tipo-terapia.model';
import { TipoTerapiaFormService } from './tipo-terapia-form.service';

import { TipoTerapiaUpdateComponent } from './tipo-terapia-update.component';

describe('TipoTerapia Management Update Component', () => {
  let comp: TipoTerapiaUpdateComponent;
  let fixture: ComponentFixture<TipoTerapiaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tipoTerapiaFormService: TipoTerapiaFormService;
  let tipoTerapiaService: TipoTerapiaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TipoTerapiaUpdateComponent],
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
      .overrideTemplate(TipoTerapiaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TipoTerapiaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tipoTerapiaFormService = TestBed.inject(TipoTerapiaFormService);
    tipoTerapiaService = TestBed.inject(TipoTerapiaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tipoTerapia: ITipoTerapia = { id: 456 };

      activatedRoute.data = of({ tipoTerapia });
      comp.ngOnInit();

      expect(comp.tipoTerapia).toEqual(tipoTerapia);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITipoTerapia>>();
      const tipoTerapia = { id: 123 };
      jest.spyOn(tipoTerapiaFormService, 'getTipoTerapia').mockReturnValue(tipoTerapia);
      jest.spyOn(tipoTerapiaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoTerapia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tipoTerapia }));
      saveSubject.complete();

      // THEN
      expect(tipoTerapiaFormService.getTipoTerapia).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tipoTerapiaService.update).toHaveBeenCalledWith(expect.objectContaining(tipoTerapia));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITipoTerapia>>();
      const tipoTerapia = { id: 123 };
      jest.spyOn(tipoTerapiaFormService, 'getTipoTerapia').mockReturnValue({ id: null });
      jest.spyOn(tipoTerapiaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoTerapia: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tipoTerapia }));
      saveSubject.complete();

      // THEN
      expect(tipoTerapiaFormService.getTipoTerapia).toHaveBeenCalled();
      expect(tipoTerapiaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITipoTerapia>>();
      const tipoTerapia = { id: 123 };
      jest.spyOn(tipoTerapiaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tipoTerapia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tipoTerapiaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
