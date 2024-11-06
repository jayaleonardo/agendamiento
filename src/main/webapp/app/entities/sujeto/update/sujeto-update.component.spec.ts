import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { SujetoService } from '../service/sujeto.service';
import { ISujeto } from '../sujeto.model';
import { SujetoFormService } from './sujeto-form.service';

import { SujetoUpdateComponent } from './sujeto-update.component';

describe('Sujeto Management Update Component', () => {
  let comp: SujetoUpdateComponent;
  let fixture: ComponentFixture<SujetoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sujetoFormService: SujetoFormService;
  let sujetoService: SujetoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SujetoUpdateComponent],
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
      .overrideTemplate(SujetoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SujetoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sujetoFormService = TestBed.inject(SujetoFormService);
    sujetoService = TestBed.inject(SujetoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const sujeto: ISujeto = { id: 456 };

      activatedRoute.data = of({ sujeto });
      comp.ngOnInit();

      expect(comp.sujeto).toEqual(sujeto);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISujeto>>();
      const sujeto = { id: 123 };
      jest.spyOn(sujetoFormService, 'getSujeto').mockReturnValue(sujeto);
      jest.spyOn(sujetoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sujeto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sujeto }));
      saveSubject.complete();

      // THEN
      expect(sujetoFormService.getSujeto).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sujetoService.update).toHaveBeenCalledWith(expect.objectContaining(sujeto));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISujeto>>();
      const sujeto = { id: 123 };
      jest.spyOn(sujetoFormService, 'getSujeto').mockReturnValue({ id: null });
      jest.spyOn(sujetoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sujeto: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sujeto }));
      saveSubject.complete();

      // THEN
      expect(sujetoFormService.getSujeto).toHaveBeenCalled();
      expect(sujetoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISujeto>>();
      const sujeto = { id: 123 };
      jest.spyOn(sujetoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sujeto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sujetoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
